package app.hivenote.socket;

import app.hivenote.component.ComponentService;
import app.hivenote.note.NoteService;
import app.hivenote.note.entity.NoteEntity;
import app.hivenote.note.mapper.NoteMapper;
import app.hivenote.socket.events.CommentEvents;
import app.hivenote.socket.events.GeneralEvents;
import app.hivenote.socket.events.NoteEvents;
import app.hivenote.socket.messages.CommentMessage;
import app.hivenote.socket.messages.NoteMessage;
import app.hivenote.socket.messages.NoteRequestMessage;
import app.hivenote.socket.messages.RoomMessage;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.util.UUID;
import lombok.*;
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
    server.addEventListener(NoteEvents.UPDATE_NOTE, NoteMessage.class, onNoteMessageReceived());
    server.addEventListener(
        CommentEvents.SEND_COMMENT, CommentMessage.class, onCommentMessageReceived());
    server.addEventListener(GeneralEvents.ROOM_REQUEST, RoomMessage.class, onRoomMessageReceived());
    server.addEventListener(
        NoteEvents.FETCH_NOTE, NoteRequestMessage.class, onNoteRequestMessageReceived());
  }

  private DataListener<NoteMessage> onNoteMessageReceived() {
    return (senderClient, data, ackSender) -> {
      String room = socketService.getRoom(senderClient, data);
      if (room == null) {
        ackSender.sendAckData(false);
        return;
      }

      socketService.sendMessage(room, NoteEvents.RETURN_NOTE, senderClient, data);
      ackSender.sendAckData(true);
    };
  }

  private DataListener<RoomMessage> onRoomMessageReceived() {
    return (senderClient, data, ackSender) -> {
      String room = data.getRoom();

      if (data.isJoining()) {
        senderClient.joinRoom(room);
        ackSender.sendAckData(true);
      } else {
        senderClient.leaveRoom(room);
        ackSender.sendAckData(false);
      }
    };
  }

  private DataListener<CommentMessage> onCommentMessageReceived() {
    return (senderClient, data, ackSender) -> {
      String room = socketService.getRoom(senderClient, data);
      if (room == null) {
        ackSender.sendAckData(false);
        return;
      }

      socketService.sendMessage(data.getRoom(), CommentEvents.RETURN_COMMENT, senderClient, data);
      ackSender.sendAckData(true);
    };
  }

  private DataListener<NoteRequestMessage> onNoteRequestMessageReceived() {
    return (senderClient, data, ackSender) -> {
      String room = socketService.getRoom(senderClient, data);
      NoteEntity note = noteService.findById(UUID.fromString(data.getId()));
      NoteMessage message = NoteMapper.toMessage(note);

      if (room == null) {
        ackSender.sendAckData(false);
        return;
      }

      socketService.sendNote(room, NoteEvents.RETURN_NOTE, senderClient, message);
      ackSender.sendAckData(message);
    };
  }

  private ConnectListener onConnected() {
    return (client) -> {
      log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
    };
  }

  private DisconnectListener onDisconnected() {
    return client -> {
      client.leaveRooms(client.getAllRooms());
      log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    };
  }
}
