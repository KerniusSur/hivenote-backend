package app.hivenote.account.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateAccountInfoRequest {
  private UUID id;
  @NotEmpty @Email private String email;
  @NotEmpty private String name;
  @NotEmpty private String lastName;
  @NotEmpty private String phoneNumber;
}
