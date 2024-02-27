package app.hivenote.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UpdatePasswordRequest {
  @NotEmpty private String oldPassword;
  @NotEmpty private String newPassword;
}
