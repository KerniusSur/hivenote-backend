package app.hivenote.account.mapper;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.AccountRoleEntity;
import app.hivenote.account.entity.RoleEntity;
import app.hivenote.auth.dto.response.RoleResponse;
import app.hivenote.utils.ListUtil;
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
