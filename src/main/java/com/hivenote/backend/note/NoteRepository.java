package com.hivenote.backend.note;

import com.hivenote.backend.note.entity.NoteAccessType;
import com.hivenote.backend.note.entity.NoteEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository
    extends JpaRepository<NoteEntity, UUID>, JpaSpecificationExecutor<NoteEntity> {
  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.account.id = :accountId AND n.id = :noteId  AND n.isDeleted = false")
  Optional<NoteEntity> findByAccountIdAndNoteId(
      @Param("accountId") UUID accountId, @Param("noteId") UUID noteId);

  @Query(
      "SELECT n FROM NoteEntity n JOIN n.accountAccess a WHERE a.account.id = :accountId AND n.id = :noteId AND a.accessType = :accessType")
  boolean existsByAccountIdAndNoteIdAndAccessType(
      @Param("accountId") UUID accountId,
      @Param("noteId") UUID noteId,
      @Param("accessType") NoteAccessType accessType);
}
