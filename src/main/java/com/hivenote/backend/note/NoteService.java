package com.hivenote.backend.note;

import static com.hivenote.backend.note.specifications.NoteSpecifications.getSpecifications;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.component.ComponentService;
import com.hivenote.backend.component.entity.ComponentEntity;
import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.note.dto.request.NoteCreateRequest;
import com.hivenote.backend.note.dto.request.NoteUpdateRequest;
import com.hivenote.backend.aws.dto.FileUploadResponse;
import com.hivenote.backend.note.entity.NoteAccessEntity;
import com.hivenote.backend.note.entity.NoteAccessType;
import com.hivenote.backend.note.entity.NoteEntity;
import com.hivenote.backend.socket.messages.NoteMessage;
import com.hivenote.backend.utils.SpecificationUtil;
import io.micrometer.common.lang.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final ComponentService componentService;
  private final AccountService accountService;
  private final String ERROR_PREFIX = "err.note.";

  public NoteService(
      NoteRepository noteRepository,
      ComponentService componentService,
      AccountService accountService) {
    this.noteRepository = noteRepository;
    this.componentService = componentService;
    this.accountService = accountService;
  }

  public NoteEntity findById(UUID id) {
    return noteRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<NoteEntity> findAllFilteredBy(
      @Nullable UUID accountId,
      @Nullable NoteAccessType accessType,
      @Nullable String searchString,
      @Nullable Boolean isArchived,
      @Nullable Boolean isDeleted) {

    return noteRepository.findAll(
        SpecificationUtil.toANDSpecification(
            getSpecifications(accountId, accessType, searchString, isArchived, isDeleted)));
  }

  public NoteEntity findByIdAndAccountId(UUID noteId, UUID accountId) {
    return noteRepository
        .findByAccountIdAndNoteId(accountId, noteId)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<NoteEntity> findRootByAccountAccessAndAccountId(
      NoteAccessType accessType, UUID accountId) {
    return noteRepository.findAll(
        SpecificationUtil.toANDSpecification(
            getSpecifications(accountId, accessType, null, false, false)));
  }

  public void saveFromSocket(NoteMessage noteMessage) {
    NoteEntity noteEntity = findById(UUID.fromString(noteMessage.getId()));
    List<ComponentEntity> components =
        new ArrayList<>(componentService.saveComponentsFromNoteMessage(noteMessage));
    components.sort(Comparator.comparing(ComponentEntity::getPriority));

    noteEntity.setCoverUrl(noteMessage.getCoverUrl()).setComponents(components);

    if (noteMessage.getTitle() != null) {
      noteEntity.setTitle(noteMessage.getTitle());
    }

    noteRepository.save(noteEntity);
  }

  public NoteEntity create(NoteCreateRequest request, UUID accountId) {
    NoteEntity parent = null;
    if (request.getParentId() != null) {
      parent = findById(request.getParentId());
      parent.getAccountAccess().stream()
          .filter(
              access ->
                  access.getAccount().getId().equals(accountId)
                          && access.getAccessType() == NoteAccessType.OWNER
                      || access.getAccessType() == NoteAccessType.EDITOR)
          .findAny()
          .orElseThrow(() -> ApiException.unauthorized(ERROR_PREFIX + "unauthorized"));
    }
    NoteEntity noteEntity =
        new NoteEntity().setTitle(request.getTitle()).setCoverUrl(request.getCoverUrl());

    if (parent != null) {
      noteEntity.setParent(parent);
    }

    List<NoteAccessEntity> noteAccessEntities =
        parent != null
            ? parent.getAccountAccess().stream()
                .map(
                    access ->
                        new NoteAccessEntity()
                            .setNote(noteEntity)
                            .setAccount(access.getAccount())
                            .setAccessType(access.getAccessType()))
                .toList()
            : List.of(
                new NoteAccessEntity()
                    .setNote(noteEntity)
                    .setAccount(new AccountEntity().setId(accountId))
                    .setAccessType(NoteAccessType.OWNER));

    noteEntity.getAccountAccess().addAll(noteAccessEntities);
    return noteRepository.save(noteEntity);
  }

  public NoteEntity update(NoteUpdateRequest request, UUID accountId) {
    NoteEntity noteEntity = findById(request.getId());

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && (access.getAccessType() == NoteAccessType.OWNER
                        || access.getAccessType() == NoteAccessType.EDITOR))) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    noteEntity
        .setTitle(request.getTitle())
        .setCoverUrl(request.getCoverUrl())
        .setIsArchived(request.getIsArchived())
        .setIsDeleted(request.getIsDeleted());

    return noteRepository.save(noteEntity);
  }

  public void delete(UUID id, UUID accountId) {
    NoteEntity noteEntity = findById(id);

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && access.getAccessType() == NoteAccessType.OWNER)) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    noteEntity.setIsDeleted(true);
    noteRepository.save(noteEntity);
  }

  public boolean hasAccess(UUID noteId, UUID accountId, NoteAccessType accessType) {
    return noteRepository.existsByAccountIdAndNoteIdAndAccessType(accountId, noteId, accessType);
  }

  public void shareNote(
      UUID noteId, UUID accountId, NoteAccessType accessType, List<String> emails) {
    NoteEntity noteEntity = findById(noteId);
    List<AccountEntity> accountsToShare =
        emails.stream().map(accountService::findByEmailOrNull).toList();

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && (access.getAccessType() == NoteAccessType.OWNER
                        || access.getAccessType() == NoteAccessType.EDITOR))) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    List<NoteAccessEntity> noteAccessEntities =
        accountsToShare.stream()
            .map(
                account ->
                    new NoteAccessEntity()
                        .setNote(noteEntity)
                        .setAccount(account)
                        .setAccessType(accessType))
            .toList();

    noteEntity.getAccountAccess().addAll(noteAccessEntities);
    noteRepository.save(noteEntity);
  }

  public List<NoteAccessEntity> findNoteCollaborators(UUID noteId, UUID accountId) {
    NoteEntity noteEntity = findById(noteId);

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && (access.getAccessType() == NoteAccessType.OWNER
                        || access.getAccessType() == NoteAccessType.EDITOR))) {
      throw ApiException.unauthorized(ERROR_PREFIX + "unauthorized");
    }

    return noteEntity.getAccountAccess();
  }
}
