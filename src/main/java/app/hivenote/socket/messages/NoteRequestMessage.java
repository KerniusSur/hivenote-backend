package app.hivenote.socket.messages;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteRequestMessage extends Message {
  private String id;
}
