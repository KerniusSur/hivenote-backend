package app.hivenote.auth.dto.response;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MeResponse {
  private UUID id;
  private String name;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Boolean isActive;
  private Boolean isEmailConfirmed;
  private List<RoleResponse> availableRoles = new LinkedList<>();
}
