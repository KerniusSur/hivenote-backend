package app.hivenote.account.dto.response;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AccountPublicResponse {
  @NotNull private UUID id;
  private String name;
  private String lastName;
  @NotNull private String email;
  private String phoneNumber;
}
