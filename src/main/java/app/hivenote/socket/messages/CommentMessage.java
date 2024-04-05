package app.hivenote.socket.messages;

import app.hivenote.socket.Message;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CommentMessage extends Message {
    private UUID id;
    private String body;
    private Integer noteLine;
    private Boolean isResolved;
    private UUID authorId;
    private UUID parentId;
}
