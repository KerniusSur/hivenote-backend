package app.hivenote.event.dto.request;

import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class EventUpdateRequest extends EventCreateRequest {
  private UUID id;
}
