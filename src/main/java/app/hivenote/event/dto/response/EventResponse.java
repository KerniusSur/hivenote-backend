package app.hivenote.event.dto.response;

import app.hivenote.account.dto.response.AccountPublicResponse;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class EventResponse {
  private UUID id;
  private String title;
  private String description;
  private String location;
  private ZonedDateTime eventStart;
  private ZonedDateTime eventEnd;
  private AccountPublicResponse createdBy;
}
