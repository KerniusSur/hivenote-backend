package app.hivenote.account.dto.response;

import app.hivenote.auth.dto.response.RoleResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AccountAdminResponse {
  private Long id;
  private String name;
  private String lastName;
  private String email;
  private String phoneNumber;
  private List<RoleResponse> roles = new ArrayList<>();
}
