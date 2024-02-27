package app.hivenote.auth.mapper;

import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.AccountRoleEntity;
import app.hivenote.account.mapper.RoleMapper;
import app.hivenote.auth.dto.response.MeResponse;
import java.util.stream.Collectors;

public class AuthMapper {
  public static MeResponse toResponse(AccountEntity entity) {
    return new MeResponse(
        entity.getId(),
        entity.getName(),
        entity.getLastName(),
        entity.getEmail(),
        entity.getPhoneNumber(),
        entity.getIsActive(),
        entity.getIsEmailConfirmed(),
        RoleMapper.toResponses(
            entity.getRoles().stream()
                .map(AccountRoleEntity::getRole)
                .collect(Collectors.toList())));
  }
}
