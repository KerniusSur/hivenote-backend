package app.hivenote.socket.messages;

import app.hivenote.component.entity.ComponentProperties;
import app.hivenote.component.entity.ComponentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class ComponentMessage extends Message {
  private String id;
  private Integer priority;
  @NotNull private ComponentType componentType;
  @NotNull private ComponentProperties properties;
}
