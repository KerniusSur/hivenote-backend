package app.hivenote.account.dto.response;

import app.hivenote.auth.dto.response.RoleResponse;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AccountResponse {
  private UUID id;
  private String name;
  private String lastName;
  private String email;
  private ZonedDateTime lastLogin;
  private List<RoleResponse> roles = new ArrayList<>();
}
