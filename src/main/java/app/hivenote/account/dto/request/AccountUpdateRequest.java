package app.hivenote.account.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountUpdateRequest extends UpdateAccountInfoRequest {
  private Boolean isActive;
  private Boolean isEmailConfirmed;
  @NotNull private List<Long> roles;
}
