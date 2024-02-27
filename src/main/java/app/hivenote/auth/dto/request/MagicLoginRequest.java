package app.hivenote.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MagicLoginRequest {
  @Email @NotEmpty private String email;
  @NotEmpty private String uuid;
}
