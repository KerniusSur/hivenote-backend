package app.hivenote.socket.events;

public interface ComponentEvents {
  // Client to server
  String SEND_COMPONENT = "SEND_COMPONENT";

  // Server to client
  String RETURN_COMPONENT = "RETURN_COMPONENT";
}
