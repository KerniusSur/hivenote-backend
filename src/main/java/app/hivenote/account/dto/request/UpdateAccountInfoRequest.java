package app.hivenote.account.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdateAccountInfoRequest {
  private Long id;
  @NotEmpty @Email private String email;
  @NotEmpty private String name;
  @NotEmpty private String lastName;
  @NotEmpty private String phoneNumber;
}
