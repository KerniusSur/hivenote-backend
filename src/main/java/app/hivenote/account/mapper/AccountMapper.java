package app.hivenote.account.mapper;

import app.hivenote.account.dto.response.AccountAdminResponse;
import app.hivenote.account.dto.response.AccountPublicResponse;
import app.hivenote.account.dto.response.AccountResponse;
import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.AccountRoleEntity;
import app.hivenote.utils.ListUtil;

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
