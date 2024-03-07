package app.hivenote.note.mapper;

import app.hivenote.account.dto.response.AccountPublicResponse;
import app.hivenote.account.mapper.AccountMapper;
import app.hivenote.comment.mapper.CommentMapper;
import app.hivenote.component.mapper.ComponentMapper;
import app.hivenote.note.dto.response.NoteMinResponse;
import app.hivenote.note.dto.response.NoteResponse;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.utils.ListUtil;
import java.util.List;
import java.util.stream.Collectors;

public class NoteMapper {
  public static NoteResponse toResponse(NoteEntity note) {
    return new NoteResponse()
        .setId(note.getId())
        .setType(note.getType())
        .setTitle(note.getTitle())
        .setCoverUrl(note.getCoverUrl())
        .setIsArchived(note.getIsArchived())
        .setIsDeleted(note.getIsDeleted())
        .setComponents(ListUtil.map(note.getComponents(), ComponentMapper::toResponse))
        .setCollaborators(toCollaboratorResponse(note))
        .setComments(ListUtil.map(note.getComments(), CommentMapper::toResponse));
  }

  public static List<AccountPublicResponse> toCollaboratorResponse(NoteEntity note) {
    return note.getAccountAccess().stream()
        .map(noteAccessEntity -> AccountMapper.toPublicResponse(noteAccessEntity.getAccount()))
        .collect(Collectors.toList());
  }

  public static NoteMinResponse toMinResponse(NoteEntity note) {
    return new NoteMinResponse().setId(note.getId()).setTitle(note.getTitle());
  }
}
