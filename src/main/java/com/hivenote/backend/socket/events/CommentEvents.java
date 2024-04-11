package com.hivenote.backend.socket.events;

public interface CommentEvents {
  // Client to server
  String SEND_COMMENT = "SEND_COMMENT";

  // Server to client
  String RETURN_COMMENT = "RETURN UPDATED";
}
