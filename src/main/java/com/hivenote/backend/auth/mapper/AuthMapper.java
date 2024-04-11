package com.hivenote.backend.auth.mapper;

import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.account.entity.AccountRoleEntity;
import com.hivenote.backend.account.mapper.RoleMapper;
import com.hivenote.backend.auth.dto.response.MeResponse;

import java.util.stream.Collectors;

public class AuthMapper {
  public static MeResponse toResponse(AccountEntity entity) {
    return new MeResponse(
        entity.getId(),
        entity.getName(),
        entity.getLastName(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        RoleMapper.toResponses(
            entity.getRoles().stream()
                .map(AccountRoleEntity::getRole)
                .collect(Collectors.toList())));
  }
}
