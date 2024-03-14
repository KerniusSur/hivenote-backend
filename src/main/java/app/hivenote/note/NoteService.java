package app.hivenote.note;

import app.hivenote.exception.ApiException;
import app.hivenote.note.dto.request.NoteCreateRequest;
import app.hivenote.note.dto.request.NoteUpdateRequest;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.notebook.entity.NotebookEntity;
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
    return noteRepository.findById(id).orElseThrow(() -> ApiException.notFound(ERROR_PREFIX + "notFound"));
  }

//  public List<>

  public NoteEntity create(NoteCreateRequest request) {
    NoteEntity noteEntity =
        new NoteEntity()
            .setType(request.getType())
            .setTitle(request.getTitle())
            .setCoverUrl(request.getCoverUrl())
            .setNotebook(new NotebookEntity().setId(request.getNotebookId()));

    return noteRepository.save(noteEntity);
  }

  public NoteEntity update(NoteUpdateRequest request) {
    NoteEntity noteEntity = findById(request.getId());
    noteEntity
        .setTitle(request.getTitle())
        .setCoverUrl(request.getCoverUrl())
        .setIsArchived(request.getIsArchived())
        .setIsDeleted(request.getIsDeleted());

    return noteRepository.save(noteEntity);
  }
}
