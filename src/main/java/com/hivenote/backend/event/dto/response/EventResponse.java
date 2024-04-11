package com.hivenote.backend.event.dto.response;

import com.hivenote.backend.account.dto.response.AccountPublicResponse;
import jakarta.validation.constraints.NotNull;
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
  @NotNull private UUID id;
  @NotNull private String title;
  private String description;
  private String location;
  @NotNull private ZonedDateTime eventStart;
  @NotNull private ZonedDateTime eventEnd;
  @NotNull private AccountPublicResponse createdBy;
}