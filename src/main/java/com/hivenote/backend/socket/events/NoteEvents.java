package com.hivenote.backend.socket.events;

public interface NoteEvents {
  // Client to server
  String FETCH_NOTE = "FETCH_NOTE";
  String UPDATE_NOTE = "UPDATE_NOTE";

  // Server to client
  String RETURN_NOTE = "RETURN_NOTE";
}
