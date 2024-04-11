package app.hivenote.note.mapper;

import app.hivenote.account.dto.response.AccountPublicResponse;
import app.hivenote.account.mapper.AccountMapper;
import app.hivenote.comment.mapper.CommentMapper;
import app.hivenote.component.mapper.ComponentMapper;
import app.hivenote.note.dto.response.NoteResponse;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.socket.messages.ComponentMessage;
import app.hivenote.socket.messages.NoteMessage;
import app.hivenote.utils.ListUtil;
import java.util.ArrayList;
import java.util.Comparator;
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

  public static NoteMessage toMessage(NoteEntity note) {
    List<ComponentMessage> components =
        new ArrayList<>(ListUtil.map(note.getComponents(), ComponentMapper::toMessage));
    components.sort(Comparator.comparingInt(ComponentMessage::getPriority));

    return new NoteMessage()
        .setId(note.getId().toString())
        .setTitle(note.getTitle())
        .setCoverUrl(note.getCoverUrl())
        .setComponents(components)
        .setComments(ListUtil.map(note.getComments(), CommentMapper::toMessage));
  }
}
