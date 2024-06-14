package com.hivenote.backend.auth;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.RoleService;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.account.entity.AccountRoleEntity;
import com.hivenote.backend.account.entity.Role;
import com.hivenote.backend.account.entity.RoleEntity;
import com.hivenote.backend.auth.dto.request.EmailPasswordLoginRequest;
import com.hivenote.backend.auth.dto.request.RegisterRequest;
import com.hivenote.backend.auth.utils.CookieUtil;
import com.hivenote.backend.auth.utils.JwtUtil;
import com.hivenote.backend.exception.ApiException;
import com.hivenote.backend.validation.ValidationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthService {
  private final AccountService accountService;
  private final JwtUtil jwtUtil;
  private final CookieUtil cookieUtil;
  private final PasswordEncoder encoder;
  private final ValidationService validationService;
  private final RoleService roleService;

  public AuthService(
      AccountService accountService,
      JwtUtil jwtUtil,
      CookieUtil cookieUtil,
      PasswordEncoder encoder,
      ValidationService validationService,
      RoleService roleService) {
    this.accountService = accountService;
    this.jwtUtil = jwtUtil;
    this.cookieUtil = cookieUtil;
    this.encoder = encoder;
    this.validationService = validationService;
    this.roleService = roleService;
  }

  public void login(EmailPasswordLoginRequest request, HttpServletResponse response) {
    String email = request.getEmail();
    String rawPassword = request.getPassword();
    AccountEntity account = accountService.findByEmail(email);
    if (!encoder.matches(rawPassword, account.getPassword())) {
      throw ApiException.internalError("Failed to login, check if email and password are correct");
    }

    account.setLastLogin(ZonedDateTime.now());
    accountService.update(account);
    addAuthorityCookie(account, response);
  }

  public void logout(HttpServletResponse response) {
    cookieUtil.deleteAuthorizationCookie(response);
  }

  public void register(RegisterRequest request, HttpServletResponse response) {
    AccountEntity account = accountService.findByEmailOrNull(request.getEmail());
    if (account != null) {
      throw ApiException.conflict("Account with email " + request.getEmail() + " already exists");
    }
    RoleEntity role = roleService.findByName(Role.USER);

    account =
        new AccountEntity()
            .setEmail(request.getEmail())
            .setPassword(encoder.encode(request.getPassword()))
            .setName(request.getName())
            .setLastLogin(ZonedDateTime.now());

    account.setRoles(List.of(new AccountRoleEntity().setRole(role).setAccount(account)));
    account = accountService.create(account);
    addAuthorityCookie(account, response);
  }

  private void addAuthorityCookie(AccountEntity user, HttpServletResponse response) {
    String authorityString =
        user.getRoles().stream()
            .map(AccountRoleEntity::getRole)
            .map(RoleEntity::getName)
            .map(Role::toString)
            .collect(Collectors.joining(","));

    List<GrantedAuthority> authorities =
        AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

    String token = jwtUtil.generateJwtToken(user, authorities);
    cookieUtil.addAuthorizationCookie(token, response);
  }
}
