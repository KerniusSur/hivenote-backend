package app.hivenote.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegisterRequest {
  @NotEmpty private String email;
  @NotEmpty private String password;
  @NotEmpty private String name;
  @NotEmpty private String lastName;
  private String phoneNumber;
}
