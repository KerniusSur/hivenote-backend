package app.hivenote.socket.messages;

import app.hivenote.socket.MessageType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Message {
  private MessageType type;
  private String room;
}
