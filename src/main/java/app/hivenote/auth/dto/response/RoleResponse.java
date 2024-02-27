package app.hivenote.auth.dto.response;

import app.hivenote.account.entity.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleResponse {
  private Long id;
  private Role name;
}
