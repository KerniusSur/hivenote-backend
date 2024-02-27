package app.hivenote.account.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AccountPublicResponse {
  @NotNull private Long id;
  private String name;
  private String lastName;
  @NotNull private String email;
  private String phoneNumber;
}
