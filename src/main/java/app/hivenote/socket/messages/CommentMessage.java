package app.hivenote.socket.messages;

import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class CommentMessage extends Message {
  private String id;
  private String body;
  private Integer noteLine;
  private Boolean isResolved;
  private UUID authorId;
  private UUID parentId;
}
