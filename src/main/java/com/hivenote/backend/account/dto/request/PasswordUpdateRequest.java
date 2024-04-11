package com.hivenote.backend.account.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PasswordUpdateRequest {
  @NotEmpty private String newPassword;
}
