package com.hivenote.backend.config;

import com.corundumstudio.socketio.ClientOperations;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketIOConfig {

  @Value("${socket.server.host}")
  private String host;

  @Value("${socket.server.port}")
  private Integer port;

  @Bean
  public SocketIOServer socketIOServer() {
    com.corundumstudio.socketio.Configuration config =
        new com.corundumstudio.socketio.Configuration();
    config.setHostname(host);
    config.setPort(port);
    return new SocketIOServer(config);
  }

  @PreDestroy
  public void onDestroy() throws Exception {
    socketIOServer().stop();
    socketIOServer().getAllClients().forEach(ClientOperations::disconnect);
  }
}
