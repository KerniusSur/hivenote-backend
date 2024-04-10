package app.hivenote.event.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EventCreateRequest {
  @NotEmpty private String title;
  private String description;
  private String location;
  @NotNull private ZonedDateTime eventStart;
  @NotNull private ZonedDateTime eventEnd;
}
