package app.hivenote.socket;

import app.hivenote.socket.messages.NoteMessage;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

  public void sendMessage(
      String room, String eventName, SocketIOClient senderClient, Message message) {
    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (!client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, message);
      }
    }
  }

  public void sendNoteInitialData(
      String room, String eventName, SocketIOClient senderClient, NoteMessage noteMessage) {
    for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
      if (client.getSessionId().equals(senderClient.getSessionId())) {
        client.sendEvent(eventName, noteMessage);
      }
    }
  }
}
