package com.hivenote.backend.component.dto.request;

import com.hivenote.backend.component.entity.ComponentProperties;
import com.hivenote.backend.component.entity.ComponentType;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ComponentUpdateRequest {
  @NotNull private UUID id;
  @NotNull private ComponentType type;
  @NotNull private ComponentProperties properties;
  @NotNull private String noteId;
  private String parentId;
}
