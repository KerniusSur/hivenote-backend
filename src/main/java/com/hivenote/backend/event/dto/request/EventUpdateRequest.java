package com.hivenote.backend.event.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class EventUpdateRequest extends EventCreateRequest {
  @NotNull private UUID id;
}
