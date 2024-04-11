package com.hivenote.backend.account.util;

import com.hivenote.backend.account.entity.Role;
import com.hivenote.backend.account.entity.RoleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountUtil {
  public static boolean hasRole(Role searchingRole, List<RoleEntity> roles) {
    return roles.stream().anyMatch(role -> role.getName().equals(searchingRole));
  }
}
