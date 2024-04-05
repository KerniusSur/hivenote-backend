package app.hivenote.socket.messages;

import app.hivenote.component.entity.ComponentProperties;
import app.hivenote.component.entity.ComponentType;
import app.hivenote.socket.Message;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ComponentMessage extends Message {
  private UUID id;
  @NotNull private ComponentType type;
  @NotNull private ComponentProperties properties;
}
