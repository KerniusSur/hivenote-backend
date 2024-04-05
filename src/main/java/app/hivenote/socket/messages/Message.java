package app.hivenote.socket.messages;

import app.hivenote.socket.MessageType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {
  private MessageType type;
  private String room;
  //  private LocalDateTime createdAt = LocalDateTime.now();
}
