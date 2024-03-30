package app.hivenote.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SocketModule {

  private final SocketIOServer server;
  private final SocketService socketService;

  public SocketModule(SocketIOServer server, SocketService socketService) {
    this.server = server;
    this.socketService = socketService;
    server.addConnectListener(onConnected());
    server.addDisconnectListener(onDisconnected());
    server.addEventListener("send_message", Message.class, onMessageReceived());
  }

  // TODO: probably will change it to note_change
  private DataListener<Message> onMessageReceived() {
    return (senderClient, data, ackSender) -> {
      log.info(data.toString());
      for (MessageData messageData : data.getData()) {
        String infoToLog =
            "Message ID:"
                + messageData.getId()
                + " Type:"
                + messageData.getType()
                + " Text:"
                + messageData.getData().getText();

        log.info(infoToLog);
      }
      socketService.sendMessage(data.getRoom(), "get_message", senderClient, data.getMessage());
    };
  }

  private ConnectListener onConnected() {
    return (client) -> {
      String room = client.getHandshakeData().getSingleUrlParam("room");
      client.joinRoom(room);
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
