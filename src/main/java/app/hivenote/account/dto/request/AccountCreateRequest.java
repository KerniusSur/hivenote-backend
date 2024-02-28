package app.hivenote.account.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AccountCreateRequest {
  private String name;
  private String lastName;
  @NotEmpty private String password;
  @NotEmpty @Email private String email;
  @NotNull private List<UUID> roles;
}
