package com.hivenote.backend.note.controller;

import com.hivenote.backend.auth.entity.AuthenticatedProfile;
import com.hivenote.backend.note.NoteService;
import com.hivenote.backend.note.dto.request.NoteShareRequest;
import com.hivenote.backend.note.dto.response.NoteAccessResponse;
import com.hivenote.backend.note.entity.NoteAccessType;
import com.hivenote.backend.note.mapper.NoteMapper;
import com.hivenote.backend.utils.ListUtil;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/notes/access")
public class NoteAccessController {
  private final NoteService noteService;

  public NoteAccessController(NoteService noteService) {
    this.noteService = noteService;
  }

  @GetMapping("/{id}/access/{accessType}")
  public boolean hasAccess(
      @PathVariable String id, @PathVariable String accessType, AuthenticatedProfile profile) {
    return noteService.hasAccess(
        UUID.fromString(id), profile.getId(), NoteAccessType.valueOf(accessType));
  }

  @PutMapping("/share")
  public void shareNote(@RequestBody NoteShareRequest request, AuthenticatedProfile profile) {
    noteService.shareNote(
        request.getNoteId(),
        profile.getId(),
        NoteAccessType.valueOf(request.getAccessType()),
        request.getEmails());
  }

  @GetMapping("/{id}")
  public List<NoteAccessResponse> findNoteCollaborators(
      @PathVariable String id, AuthenticatedProfile profile) {
    return ListUtil.map(
        noteService.findNoteCollaborators(UUID.fromString(id), profile.getId()),
        NoteMapper::toNoteAccessResponse);
  }
}
