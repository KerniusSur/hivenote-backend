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
    NoteResponse response =
        new NoteResponse()
            .setId(note.getId())
            .setTitle(note.getTitle())
            .setCoverUrl(note.getCoverUrl())
            .setIsArchived(note.getIsArchived())
            .setIsDeleted(note.getIsDeleted())
            .setCollaborators(toCollaboratorResponse(note));

    if (note.getComponents() != null) {
      response.setComponents(ListUtil.map(note.getComponents(), ComponentMapper::toResponse));
    }

    if (note.getComments() != null) {
      response.setComments(ListUtil.map(note.getComments(), CommentMapper::toResponse));
    }

//    if (note.getParent() != null) {
//      response.setParent(toResponse(note.getParent()));
//    }

    if (note.getChildren() != null) {
      response.setChildren(ListUtil.map(note.getChildren(), NoteMapper::toResponse));
    }

    return response;
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
