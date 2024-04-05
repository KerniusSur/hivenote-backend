package app.hivenote.note;

import app.hivenote.auth.entity.AuthenticatedProfile;
import app.hivenote.note.dto.request.NoteCreateRequest;
import app.hivenote.note.dto.request.NoteUpdateRequest;
import app.hivenote.note.dto.response.NoteResponse;
import app.hivenote.note.entity.NoteAccessType;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.note.mapper.NoteMapper;
import app.hivenote.utils.ListUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@Tag(name = "note")
@Transactional
@RestController
@RequestMapping("/api/v1/user/notes")
public class NoteController {
  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("/{id}")
  public NoteResponse findById(@PathVariable String id, AuthenticatedProfile profile) {
    return NoteMapper.toResponse(
        noteService.findByIdAndAccountId(UUID.fromString(id), profile.getId()));
  }

  @GetMapping("/owner")
  public List<NoteResponse> findAllRootNotesWithOwnerAccess(AuthenticatedProfile profile) {
    return ListUtil.map(
        noteService.findRootByAccountAccessAndAccountId(NoteAccessType.OWNER, profile.getId()),
        NoteMapper::toResponse);
  }

  @GetMapping("/shared")
  public List<NoteResponse> findAllRootNotesWithSharedAccess(AuthenticatedProfile profile) {
    List<NoteEntity> notes =
        noteService.findRootByAccountAccessAndAccountId(NoteAccessType.EDITOR, profile.getId());
    notes.addAll(
        noteService.findRootByAccountAccessAndAccountId(NoteAccessType.VIEWER, profile.getId()));

    return ListUtil.map(notes, NoteMapper::toResponse);
  }

  @PostMapping
  public NoteResponse create(@RequestBody NoteCreateRequest request, AuthenticatedProfile profile) {
    return NoteMapper.toResponse(noteService.create(request, profile.getId()));
  }

  @PutMapping
  public NoteResponse update(@RequestBody NoteUpdateRequest request, AuthenticatedProfile profile) {
    return NoteMapper.toResponse(noteService.update(request, profile.getId()));
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable String id, AuthenticatedProfile profile) {
    noteService.delete(UUID.fromString(id), profile.getId());
  }
}
