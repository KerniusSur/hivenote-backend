package com.hivenote.backend.note;

import com.hivenote.backend.note.entity.NoteAccessEntity;
import com.hivenote.backend.note.entity.NoteAccessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteAccessRepository
extends JpaRepository<NoteAccessEntity, UUID> {
    boolean existsByNoteIdAndAccountId(UUID noteId, UUID accountId);

    boolean existsByNoteIdAndAccountIdAndAccessType(UUID noteId, UUID accountId, NoteAccessType accessType);

    Optional<NoteAccessEntity> findByNoteIdAndAccountId(UUID noteId, UUID accountId);
}
