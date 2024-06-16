package com.hivenote.backend.note;

import static com.hivenote.backend.note.specifications.NoteSpecifications.getSpecifications;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.component.ComponentService;
import com.hivenote.backend.component.entity.ComponentEntity;
import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.note.dto.request.NoteCreateRequest;
import com.hivenote.backend.note.dto.request.NoteUpdateRequest;
import com.hivenote.backend.note.entity.NoteAccessEntity;
import com.hivenote.backend.note.entity.NoteAccessType;
import com.hivenote.backend.note.entity.NoteEntity;
import com.hivenote.backend.socket.messages.NoteMessage;
import com.hivenote.backend.utils.SpecificationUtil;
import io.micrometer.common.lang.Nullable;
import java.util.*;

import jakarta.validation.constraints.Null;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final ComponentService componentService;
  private final AccountService accountService;
  private final NoteAccessRepository noteAccessRepository;

  public NoteService(
      NoteRepository noteRepository,
      ComponentService componentService,
      AccountService accountService,
      NoteAccessRepository noteAccessRepository) {
    this.noteRepository = noteRepository;
    this.componentService = componentService;
    this.accountService = accountService;
    this.noteAccessRepository = noteAccessRepository;
  }

  public NoteEntity findById(UUID id) {
    return noteRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound("Note was not found"));
  }

  public List<NoteEntity> findAllFilteredBy(
      @Nullable UUID accountId,
      @Nullable NoteAccessType accessType,
      @Nullable NoteAccessType accessType2,
      @Nullable String searchString,
      @Nullable Boolean isArchived,
      @Nullable Boolean isDeleted) {

    return noteRepository.findAll(
        SpecificationUtil.toANDSpecification(
            getSpecifications(accountId, accessType, accessType2, searchString, isArchived, isDeleted)));
  }

  public NoteEntity findByIdAndAccountId(UUID noteId, UUID accountId) {
    return noteRepository
        .findByAccountIdAndNoteId(accountId, noteId)
        .orElseThrow(() -> ApiException.notFound("Note was not found"));
  }

  public List<NoteEntity> findRootByAccountAccessAndAccountId(
      NoteAccessType accessType, UUID accountId) {
    return noteRepository.findAll(
        SpecificationUtil.toANDSpecification(
            getSpecifications(accountId, accessType, null, null, false, false)));
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
          .orElseThrow(
              () -> ApiException.unauthorized("You are not authorized to perform this action"));
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
      throw ApiException.unauthorized("You are not authorized to update this note");
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
      throw ApiException.unauthorized("You are not authorized to delete this note");
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
    AccountEntity accountEntity = accountService.findById(accountId);
    List<AccountEntity> accountsToShare =
        new ArrayList<>(emails.stream().map(accountService::findByEmailOrNull).toList());

    accountsToShare.removeIf(Objects::isNull);

    if (noteEntity.getAccountAccess().stream()
        .noneMatch(
            access ->
                access.getAccount().getId().equals(accountId)
                    && (access.getAccessType() == NoteAccessType.OWNER
                        || access.getAccessType() == NoteAccessType.EDITOR))) {
      throw ApiException.unauthorized("You are not authorized to share this note");
    }

    // TODO: Luzta kai bandai is viewew i editor pakelti
    accountsToShare =
        accountsToShare.stream()
            .filter(
                account ->
                    !noteAccessRepository.existsByNoteIdAndAccountIdAndAccessType(
                        noteId, account.getId(), accessType))
            .toList();

    List<NoteAccessEntity> noteAccessEntities =
        accountsToShare.stream()
            .map(
                account -> {
                  // Check if the account is already a collaborator
                  NoteAccessEntity entity =
                      noteAccessRepository
                          .findByNoteIdAndAccountId(noteId, account.getId())
                          .orElse(null);

                  if (entity != null) {
                    // If the account is a collaborator, check if the collaborator is an editor and
                    // make sure that the request was sent by the owner
                    if (entity.getAccessType() == NoteAccessType.EDITOR
                        && !noteAccessRepository.existsByNoteIdAndAccountIdAndAccessType(
                            noteId, account.getId(), NoteAccessType.OWNER)) {
                      throw ApiException.unauthorized("You are not authorized to share this note");
                    }

                    return entity.setAccessType(accessType);
                  }
                  entity =
                      new NoteAccessEntity()
                          .setNote(noteEntity)
                          .setAccount(account)
                          .setAccessType(accessType);

                  return entity;
                })
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
      throw ApiException.unauthorized("You are not authorized to view this note's collaborators");
    }

    return noteEntity.getAccountAccess();
  }
}
