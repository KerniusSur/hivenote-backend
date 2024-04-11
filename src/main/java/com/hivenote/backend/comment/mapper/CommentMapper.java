package com.hivenote.backend.comment.mapper;

import com.hivenote.backend.account.mapper.AccountMapper;
import com.hivenote.backend.comment.dto.response.CommentResponse;
import com.hivenote.backend.comment.entity.CommentEntity;
import com.hivenote.backend.socket.messages.CommentMessage;

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

  public static CommentMessage toMessage(CommentEntity comment) {
    return new CommentMessage()
        .setId(comment.getId().toString())
        .setBody(comment.getBody())
        .setNoteLine(comment.getNoteLine())
        .setIsResolved(comment.getIsResolved())
        .setAuthorId(comment.getAccount().getId())
        .setParentId(comment.getParent() == null ? null : comment.getParent().getId());
  }
}
