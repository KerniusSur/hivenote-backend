package app.hivenote.account.dto.response;

import app.hivenote.auth.dto.response.RoleResponse;
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
public class AccountAdminResponse {
  private UUID id;
  private String name;
  private String lastName;
  private String email;
  private String phoneNumber;
  private List<RoleResponse> roles = new ArrayList<>();
}
