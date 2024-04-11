package com.hivenote.backend.account.mapper;

import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.account.entity.AccountRoleEntity;
import com.hivenote.backend.account.entity.RoleEntity;
import com.hivenote.backend.auth.dto.response.RoleResponse;
import com.hivenote.backend.utils.ListUtil;

import java.util.List;
import java.util.UUID;

public class RoleMapper {
  public static RoleResponse toResponse(RoleEntity entity) {
    return new RoleResponse(entity.getId(), entity.getName());
  }

  public static List<RoleResponse> toResponses(List<RoleEntity> entities) {
    return ListUtil.map(entities, RoleMapper::toResponse);
  }

  public static AccountRoleEntity toEntity(UUID id, AccountEntity account) {
    return new AccountRoleEntity().setAccount(account).setRole(new RoleEntity().setId(id));
  }
}
