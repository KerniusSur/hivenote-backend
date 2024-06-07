package com.hivenote.backend.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerCommandLineRunner implements CommandLineRunner {
  private final SocketIOServer server;

  @Value("${aws.isDisabled}")
  private boolean isDisabled;

  @Autowired
  public ServerCommandLineRunner(SocketIOServer server) {
    this.server = server;
  }

  @Override
  public void run(String... args) throws Exception {
    if (isDisabled) {
      log.info("AWS is disabled. File upload will not work. Server is starting...");
    }

    server.start();
  }
}
