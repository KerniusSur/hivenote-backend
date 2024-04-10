package app.hivenote.note;

import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository
    extends JpaRepository<NoteEntity, UUID>, JpaSpecificationExecutor<NoteEntity> {
  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.accessType = :accessType AND a.account.id = :accountId AND n.isDeleted = false")
  List<NoteEntity> findByAccountAccessAndAccountId(
      @Param("accessType") NoteAccessType accessType, @Param("accountId") UUID accountId);

  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.accessType = :accessType AND a.account.id = :accountId AND n.parent IS NULL AND n.isDeleted = false")
  List<NoteEntity> findRootByAccountAccessAndAccountId(
      @Param("accessType") NoteAccessType accessType, @Param("accountId") UUID accountId);

  //      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.account.id = :accountId AND
  // n.id = :noteId  AND (n.isDeleted = false OR n.isDeleted IS NULL)")
  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.account.id = :accountId AND n.id = :noteId  AND n.isDeleted = false")
  Optional<NoteEntity> findByAccountIdAndNoteId(
      @Param("accountId") UUID accountId, @Param("noteId") UUID noteId);

  Optional<NoteEntity> findByIdAndIsDeleted(UUID id, Boolean isDeleted);
}
