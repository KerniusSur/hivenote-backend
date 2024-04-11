package app.hivenote.note;

import static app.hivenote.note.specifications.NoteSpecifications.getSpecifications;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.component.ComponentService;
import app.hivenote.component.entity.ComponentEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.note.dto.request.NoteCreateRequest;
import app.hivenote.note.dto.request.NoteUpdateRequest;
import app.hivenote.note.entity.NoteAccessEntity;
import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.socket.messages.NoteMessage;
import app.hivenote.utils.SpecificationUtil;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.AccessType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteService {
  private final NoteRepository noteRepository;
  private final ComponentService componentService;
  private final String ERROR_PREFIX = "err.note.";

  public NoteService(NoteRepository noteRepository, ComponentService componentService) {
    this.noteRepository = noteRepository;
    this.componentService = componentService;
  }

  public NoteEntity findById(UUID id) {
    return noteRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<NoteEntity> findAllFilteredBy(
      @Nullable UUID accountId,
      @Nullable AccessType accessType,
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
    return noteRepository.findRootByAccountAccessAndAccountId(accessType, accountId);
  }

  public void saveFromSocket(NoteMessage noteMessage) {
    NoteEntity noteEntity = findById(UUID.fromString(noteMessage.getId()));
    List<ComponentEntity> components =
        new ArrayList<>(componentService.saveComponentsFromNoteMessage(noteMessage));
    components.sort(Comparator.comparing(ComponentEntity::getPriority));

    noteEntity
        .setTitle(noteMessage.getTitle())
        .setCoverUrl(noteMessage.getCoverUrl())
        .setComponents(components);

    NoteEntity savedEntity = noteRepository.save(noteEntity);
    log.info("Note saved: {}", savedEntity);
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
}
