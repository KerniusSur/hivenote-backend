package app.hivenote.socket;

import app.hivenote.component.ComponentService;
import app.hivenote.note.NoteService;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.note.mapper.NoteMapper;
import app.hivenote.socket.messages.CommentMessage;
import app.hivenote.socket.messages.NoteMessage;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {

  private final SocketIOServer server;
  private final SocketService socketService;
  private final ComponentService componentService;
  private final NoteService noteService;

  public SocketModule(
      SocketIOServer server,
      SocketService socketService,
      ComponentService componentService,
      NoteService noteService) {
    this.server = server;
    this.socketService = socketService;
    this.componentService = componentService;
    this.noteService = noteService;

    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
    server.addEventListener("send_message", NoteMessage.class, onNoteMessageReceived());
    server.addEventListener("send_comment", CommentMessage.class, onCommentMessageReceived());
  }

  private DataListener<NoteMessage> onNoteMessageReceived() {
    return (senderClient, data, ackSender) -> {
      socketService.sendMessage(data.getRoom(), "get_message", senderClient, data);
      ackSender.sendAckData(true);
    };
  }

  private DataListener<CommentMessage> onCommentMessageReceived() {
    return (senderClient, data, ackSender) -> {
      socketService.sendMessage(data.getRoom(), "get_comment", senderClient, data);
      ackSender.sendAckData(true);
    };
  }

  private ConnectListener onConnected() {
    return (client) -> {
      String room = client.getHandshakeData().getSingleUrlParam("room");
      client.joinRoom(room);
      NoteEntity note = noteService.findById(UUID.fromString(room));
      NoteMessage message = NoteMapper.toMessage(note);

      socketService.sendNoteInitialData(room, "get_init_note", client, message);
      log.info(
          "Socket ID[{}]  Connected to socket in room [{}]",
          client.getSessionId().toString(),
          room);
    };
  }

  private DisconnectListener onDisconnected() {
    return client -> {
      log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    };
  }
}
