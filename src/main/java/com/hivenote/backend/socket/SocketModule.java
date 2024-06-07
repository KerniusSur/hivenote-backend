package com.hivenote.backend.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.hivenote.backend.note.NoteService;
import com.hivenote.backend.note.entity.NoteEntity;
import com.hivenote.backend.note.mapper.NoteMapper;
import com.hivenote.backend.socket.events.CommentEvents;
import com.hivenote.backend.socket.events.GeneralEvents;
import com.hivenote.backend.socket.events.NoteEvents;
import com.hivenote.backend.socket.messages.CommentMessage;
import com.hivenote.backend.socket.messages.NoteMessage;
import com.hivenote.backend.socket.messages.NoteRequestMessage;
import com.hivenote.backend.socket.messages.RoomMessage;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// TODO: add note validations and error handling (note size, )

@Slf4j
@Component
public class SocketModule {
  private final SocketIOServer server;
  private final SocketService socketService;
  private final NoteService noteService;

  public SocketModule(SocketIOServer server, SocketService socketService, NoteService noteService) {
    this.server = server;
    this.socketService = socketService;
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

      noteService.saveFromSocket(data);
      socketService.sendNoteToOthers(room, NoteEvents.RETURN_NOTE, senderClient, data);
      ackSender.sendAckData(true);
    };
  }

  private DataListener<RoomMessage> onRoomMessageReceived() {
    return (senderClient, data, ackSender) -> {
      String room = data.getRoom();

      if (data.getIsJoining()) {
        senderClient.joinRoom(room);
        ackSender.sendAckData(true);
      } else {
        senderClient.leaveRoom(room);
        ackSender.sendAckData(false);
      }
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

      socketService.sendNoteToOrigin(room, NoteEvents.RETURN_NOTE, senderClient, message);
      ackSender.sendAckData(message);
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

  private ConnectListener onConnected() {
    return (client) -> {
      log.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
      log.info("Server configuration [{}]", server.getConfiguration());
    };
  }

  private DisconnectListener onDisconnected() {
    return client -> {
      client.leaveRooms(client.getAllRooms());
      client.disconnect();
      log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
    };
  }
}
