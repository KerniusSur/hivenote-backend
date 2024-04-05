package app.hivenote.socket;

import app.hivenote.note.NoteService;
import app.hivenote.socket.messages.*;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {
  private final NoteService noteService;

    public SocketService(NoteService noteService) {
        this.noteService = noteService;
    }

  public void sendMessage(
      String room, String eventName, SocketIOClient senderClient, Message message) {
    System.out.println("Event: " + eventName);
    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (!client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, message);
      }
    }
  }

  public void sendNote(
      String room, String eventName, SocketIOClient senderClient, NoteMessage noteMessage) {
    System.out.println("Event: " + eventName);
    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, noteMessage);
      }
    }
  }

  public void sendComponent(
      String room,
      String eventName,
      SocketIOClient senderClient,
      ComponentMessage componentMessage) {
    System.out.println("Event: " + eventName);
    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (!client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, componentMessage);
      }
    }
  }

  public String getRoom(SocketIOClient client, NoteMessage message) {
    return message.getRoom() == null
        ? (message.getId() == null
            ? client.getHandshakeData().getSingleUrlParam("room")
            : message.getId())
        : message.getRoom();
  }

  public String getRoom(SocketIOClient client, CommentMessage message) {
    return message.getRoom() == null
        ? (message.getId() == null
            ? client.getHandshakeData().getSingleUrlParam("room")
            : message.getId())
        : message.getRoom();
  }

  public String getRoom(SocketIOClient client, ComponentMessage message) {
    return message.getRoom() == null
        ? (message.getId() == null
            ? client.getHandshakeData().getSingleUrlParam("room")
            : message.getId())
        : message.getRoom();
  }

  public String getRoom(SocketIOClient client, NoteRequestMessage message) {
    return message.getRoom() == null
        ? (message.getId() == null
            ? client.getHandshakeData().getSingleUrlParam("room")
            : message.getId())
        : message.getRoom();
  }

  public String getRoom(SocketIOClient client) {
    return client.getHandshakeData().getSingleUrlParam("room");
  }
}
