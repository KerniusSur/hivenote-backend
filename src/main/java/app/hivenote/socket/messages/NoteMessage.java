package app.hivenote.socket.messages;

import app.hivenote.socket.Message;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NoteMessage extends Message {
  private UUID id;
  private String title;
  private String coverUrl;
  private List<ComponentMessage> components;
  private List<CommentMessage> comments;
}
