package app.hivenote.event.dto.request;

import java.time.ZonedDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EventCreateRequest {
  private String title;
  private String description;
  private String location;
  private ZonedDateTime eventStart;
  private ZonedDateTime eventEnd;
}
