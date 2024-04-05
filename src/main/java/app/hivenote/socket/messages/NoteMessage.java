package app.hivenote.socket.messages;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteMessage extends Message {
  private String id;
  private String title;
  private String coverUrl;
  private List<ComponentMessage> components;
  private List<CommentMessage> comments;
}
