package com.hivenote.backend.component.dto.response;

import com.hivenote.backend.component.entity.ComponentProperties;
import com.hivenote.backend.component.entity.ComponentType;
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
  private Integer priority;
  private ComponentProperties properties;
  private ComponentResponse parent;
  private List<ComponentResponse> children;
}
