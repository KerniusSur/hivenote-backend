package com.hivenote.backend.socket.messages;

import com.hivenote.backend.component.entity.ComponentProperties;
import com.hivenote.backend.component.entity.ComponentType;
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
