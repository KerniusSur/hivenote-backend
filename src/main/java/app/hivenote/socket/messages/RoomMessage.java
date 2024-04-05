package app.hivenote.socket.messages;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoomMessage {
  private boolean isJoining;
  private String room;
}
