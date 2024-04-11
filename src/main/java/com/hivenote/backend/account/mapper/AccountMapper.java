package com.hivenote.backend.account.mapper;

import com.hivenote.backend.account.dto.response.AccountAdminResponse;
import com.hivenote.backend.account.dto.response.AccountPublicResponse;
import com.hivenote.backend.account.dto.response.AccountResponse;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.account.entity.AccountRoleEntity;
import com.hivenote.backend.utils.ListUtil;

public class AccountMapper {
  public static AccountResponse toResponse(AccountEntity entity) {
    return new AccountResponse(
        entity.getId(),
        entity.getName(),
        entity.getLastName(),
        entity.getEmail(),
        entity.getLastLogin(),
        RoleMapper.toResponses(ListUtil.map(entity.getRoles(), AccountRoleEntity::getRole)));
  }

  public static AccountAdminResponse toAdminResponse(AccountEntity entity) {
    return new AccountAdminResponse(
        entity.getId(),
        entity.getName(),
        entity.getLastName(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        RoleMapper.toResponses(ListUtil.map(entity.getRoles(), AccountRoleEntity::getRole)));
  }

  public static AccountPublicResponse toPublicResponse(AccountEntity entity) {
    return new AccountPublicResponse(
        entity.getId(),
        entity.getName(),
        entity.getLastName(),
        entity.getEmail(),
        entity.getPhoneNumber());
  }
}
