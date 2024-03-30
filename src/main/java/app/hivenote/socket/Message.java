package app.hivenote.socket;

import com.fasterxml.jackson.annotation.JsonValue;
import java.util.List;
import lombok.Data;

@Data
public class Message {
  private MessageType type;
  private String message;
  private String room;
  @JsonValue private List<MessageData> data;

  public Message() {}

  public Message(MessageType type, String message) {
    this.type = type;
    this.message = message;
  }
}
