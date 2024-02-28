package app.hivenote.auth.dto.response;

import app.hivenote.account.entity.Role;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleResponse {
  private UUID id;
  private Role name;
}
