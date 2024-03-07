package app.hivenote.comment.mapper;

import app.hivenote.account.mapper.AccountMapper;
import app.hivenote.comment.dto.response.CommentResponse;
import app.hivenote.comment.entity.CommentEntity;
import java.util.stream.Collectors;

public class CommentMapper {
  public static CommentResponse toResponse(CommentEntity comment) {
    return new CommentResponse()
        .setId(comment.getId())
        .setBody(comment.getBody())
        .setNoteLine(comment.getNoteLine())
        .setIsResolved(comment.getIsResolved())
        .setAccount(AccountMapper.toPublicResponse(comment.getAccount()))
        .setParent(
            comment.getParent() == null
                ? null
                : toResponse(comment.getParent())
                    .setChildren(
                        comment.getChildren() == null
                            ? null
                            : comment.getChildren().stream()
                                .map(CommentMapper::toResponse)
                                .collect(Collectors.toList())));
  }
}
