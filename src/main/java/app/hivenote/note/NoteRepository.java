package app.hivenote.note;

import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {
  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.accessType = :accessType AND a.account.id = :accountId")
  List<NoteEntity> findByAccountAccessAndAccountId(
      @Param("accessType") NoteAccessType accessType, @Param("accountId") UUID accountId);

  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.account.id = :accountId AND a.note.id = :noteId")
  List<NoteEntity> findByAccountIdAndNoteId(
      @Param("accountId") UUID accountId, @Param("noteId") UUID noteId);
}
