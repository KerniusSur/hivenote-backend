package com.hivenote.backend.account.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientCreateRequest {
  @NotEmpty private String name;
  @NotEmpty private String lastName;
  @NotEmpty private String email;
  @NotEmpty private String phoneNumber;
}
