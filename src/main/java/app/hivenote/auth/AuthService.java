package app.hivenote.auth;

import app.hivenote.account.AccountService;
import app.hivenote.account.RoleService;
import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.AccountRoleEntity;
import app.hivenote.account.entity.Role;
import app.hivenote.account.entity.RoleEntity;
import app.hivenote.auth.dto.request.EmailPasswordLoginRequest;
import app.hivenote.auth.dto.request.RegisterRequest;
import app.hivenote.auth.utils.CookieUtil;
import app.hivenote.auth.utils.JwtUtil;
import app.hivenote.exception.ApiException;
import app.hivenote.validation.ValidationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

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
      throw ApiException.internalError("err.cant.login");
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
      throw ApiException.conflict("err.account.exists");
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
