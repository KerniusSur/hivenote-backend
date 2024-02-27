package app.hivenote.account.util;

import app.hivenote.account.entity.Role;
import app.hivenote.account.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountUtil {
  public static boolean hasRole(Role searchingRole, List<RoleEntity> roles) {
    return roles.stream().anyMatch(role -> role.getName().equals(searchingRole));
  }
}
