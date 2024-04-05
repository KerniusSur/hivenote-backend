package app.hivenote.component.dto.response;

import app.hivenote.component.entity.ComponentProperties;
import app.hivenote.component.entity.ComponentType;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ComponentResponse {
  private UUID id;
  private ComponentType type;
  private ComponentProperties properties;
  private ComponentResponse parent;
  private List<ComponentResponse> children;
}
