package app.hivenote.socket;

import java.time.ZonedDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Message {
  private MessageType type;
  private String room;
  private ZonedDateTime timestamp;
}
