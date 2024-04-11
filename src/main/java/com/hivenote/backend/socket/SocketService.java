package com.hivenote.backend.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.hivenote.backend.note.NoteService;
import com.hivenote.backend.socket.messages.CommentMessage;
import com.hivenote.backend.socket.messages.Message;
import com.hivenote.backend.socket.messages.NoteMessage;
import com.hivenote.backend.socket.messages.NoteRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SocketService {
    private final NoteService noteService;

    public SocketService(NoteService noteService) {
        this.noteService = noteService;
    }

    public void sendMessage(String room, String eventName, SocketIOClient senderClient, Message message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, message);
            }
        }
    }

    public void sendNoteToOrigin(String room, String eventName, SocketIOClient senderClient, NoteMessage noteMessage) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, noteMessage);
            }
        }
    }

    public void sendNoteToOthers(String room, String eventName, SocketIOClient senderClient, NoteMessage noteMessage) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(room).getClients()) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent(eventName, noteMessage);
            }
        }
    }

    public String getRoom(SocketIOClient client, NoteMessage message) {
        return message.getRoom() == null ? (message.getId() == null ? client.getHandshakeData().getSingleUrlParam("room") : message.getId()) : message.getRoom();
    }

    public String getRoom(SocketIOClient client, CommentMessage message) {
        return message.getRoom() == null ? (message.getId() == null ? client.getHandshakeData().getSingleUrlParam("room") : message.getId()) : message.getRoom();
    }

    public String getRoom(SocketIOClient client, NoteRequestMessage message) {
        return message.getRoom() == null ? (message.getId() == null ? client.getHandshakeData().getSingleUrlParam("room") : message.getId()) : message.getRoom();
    }
}
