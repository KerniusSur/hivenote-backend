package app.hivenote.note;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.note.dto.request.NoteCreateRequest;
import app.hivenote.note.dto.request.NoteUpdateRequest;
import app.hivenote.note.entity.NoteAccessEntity;
import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
  private final NoteRepository noteRepository;
  private final String ERROR_PREFIX = "err.note.";

  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public NoteEntity findById(UUID id) {
    return noteRepository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public NoteEntity findByIdAndIsDeleted(UUID id, Boolean isDeleted) {
    return noteRepository
        .findByIdAndIsDeleted(id, isDeleted)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public NoteEntity findByIdAndAccountId(UUID noteId, UUID accountId) {
    return noteRepository
        .findByAccountIdAndNoteId(accountId, noteId)
        .orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

  public List<NoteEntity> findByAccountAccessAndAccountId(
      NoteAccessType accessType, UUID accountId) {
    return noteRepository.findByAccountAccessAndAccountId(accessType, accountId);
  }

  public List<NoteEntity> findRootByAccountAccessAndAccountId(
      NoteAccessType accessType, UUID accountId) {
    return noteRepository.findRootByAccountAccessAndAccountId(accessType, accountId);
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

    for (NoteAccessEntity access : noteAccessEntities) {
      System.out.println("ID --->" + access.getAccount().getId());
      System.out.println("ACCESS --->" + access.getAccessType());
    }

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
