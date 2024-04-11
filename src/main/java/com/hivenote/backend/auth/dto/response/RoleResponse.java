package com.hivenote.backend.auth.dto.response;

import com.hivenote.backend.account.entity.Role;
import java.util.UUID;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RoleResponse {
  private UUID id;
  private Role name;
}
