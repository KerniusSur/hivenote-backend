package app.hivenote.note;

import app.hivenote.note.entity.NoteEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteEntity, UUID> {}
