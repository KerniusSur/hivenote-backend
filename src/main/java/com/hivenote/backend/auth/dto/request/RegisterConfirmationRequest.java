package com.hivenote.backend.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RegisterConfirmationRequest {
  @NotEmpty private String name;
  @NotEmpty private String lastName;
}
