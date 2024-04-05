package app.hivenote.component.dto.request;

import app.hivenote.component.entity.ComponentProperties;
import app.hivenote.component.entity.ComponentType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ComponentCreateRequest {
  @NotNull private ComponentType type;
  @NotNull private ComponentProperties properties;
  @NotNull private String noteId;
  private String parentId;
}
