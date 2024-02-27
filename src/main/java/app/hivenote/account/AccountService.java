package app.hivenote.account;

import app.hivenote.account.dto.request.AccountCreateRequest;
import app.hivenote.account.dto.request.AccountUpdateRequest;
import app.hivenote.account.dto.request.UpdateAccountInfoRequest;
import app.hivenote.account.entity.AccountEntity;
import app.hivenote.account.entity.AccountRoleEntity;
import app.hivenote.account.entity.RoleEntity;
import app.hivenote.exception.ApiException;
import app.hivenote.utils.SpecificationUtil;
import app.hivenote.validation.ValidationService;
import app.hivenote.validation.entity.ValidationType;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
  private static final String ERROR_PREFIX = "err.account.";
  private final RoleService roleService;
  private final AccountRepository repository;
  private final PasswordEncoder encoder;
  private final ValidationService validationService;

  public AccountService(
      RoleService roleService,
      AccountRepository repository,
      PasswordEncoder encoder,
      ValidationService validationService) {
    this.roleService = roleService;
    this.repository = repository;
    this.encoder = encoder;
    this.validationService = validationService;
  }

  public List<AccountEntity> findAll() {
    return repository.findAll(SpecificationUtil.sortByIdAsc());
  }

  public AccountEntity findById(Long id) throws ApiException {
    return repository
        .findById(id)
        .orElseThrow(() -> ApiException.notFound("err.account.dont.exist"));
  }

  public List<AccountEntity> findAllClientsBySpecialist(Long id) {
    return repository
        .findCreatedClients(id)
        .orElseThrow(() -> ApiException.notFound("err.account.dont.exist"));
  }

  public AccountEntity findByIdOrNull(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Boolean existsById(Long id) {
    return repository.existsById(id);
  }

  public AccountEntity findByPasswordResetToken(String token) throws ApiException {
    return repository
        .findByPasswordResetToken(token)
        .orElseThrow(() -> ApiException.notFound("err.account.dont.exist"));
  }

  public AccountEntity findByEmail(String email) throws ApiException {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> ApiException.notFound("err.account.dont.exist"));
  }

  public AccountEntity findByEmailOrNull(String email) {
    return repository.findByEmail(email).orElse(null);
  }

  public AccountEntity create(AccountCreateRequest request) throws ApiException {
    String email = request.getEmail().toLowerCase();
    Optional<AccountEntity> byEmail = repository.findByEmail(email);

    if (byEmail.isPresent()) {
      throw ApiException.bad(ERROR_PREFIX + "exists");
    }

    AccountEntity account =
        new AccountEntity()
            .setEmail(email)
            .setName(request.getName())
            .setLastName(request.getLastName())
            .setLastLogin(ZonedDateTime.now())
            .setPassword(request.getPassword());

    for (Long id : request.getRoles()) {
      Optional<RoleEntity> role = roleService.findById(id);
      if (role.isEmpty()) {
        throw ApiException.bad("err.role.dont.exist");
      }
      RoleEntity roleEntity = role.get();
      AccountRoleEntity accountRoleEntity =
          new AccountRoleEntity().setRole(roleEntity).setAccount(account);
      account.getRoles().add(accountRoleEntity);
    }

    return repository.save(account);
  }

  public AccountEntity create(AccountEntity account) throws ApiException {
    return repository.save(account);
  }

  public AccountEntity create(AccountUpdateRequest request) throws ApiException {
    String email = request.getEmail().toLowerCase();
    Optional<AccountEntity> byEmail = repository.findByEmail(email);

    if (byEmail.isPresent()) {
      throw ApiException.bad(ERROR_PREFIX + "exists");
    }

    AccountEntity account =
        new AccountEntity()
            .setEmail(email)
            .setName(request.getName())
            .setLastName(request.getLastName())
            .setLastLogin(ZonedDateTime.now())
            .setPhoneNumber(request.getPhoneNumber());

    for (Long id : request.getRoles()) {
      Optional<RoleEntity> role = roleService.findById(id);
      if (role.isEmpty()) {
        throw ApiException.bad("err.role.dont.exist");
      }
      RoleEntity roleEntity = role.get();
      AccountRoleEntity accountRoleEntity =
          new AccountRoleEntity().setRole(roleEntity).setAccount(account);
      account.getRoles().add(accountRoleEntity);
    }

    return repository.save(account);
  }

  public AccountEntity update(AccountUpdateRequest request) {
    AccountEntity account = findById(request.getId());
    if (account == null) {
      throw ApiException.bad(ERROR_PREFIX + "dont.exist");
    }

    List<Long> roleIdList = new ArrayList<>();
    List<AccountRoleEntity> accountRoleEntities = account.getRoles();
    for (AccountRoleEntity entity : accountRoleEntities) {
      roleIdList.add(entity.getRole().getId());
    }

    for (Long id : request.getRoles()) {
      Optional<RoleEntity> role = roleService.findById(id);
      if (role.isEmpty()) {
        throw ApiException.bad("err.role.dont.exist");
      }
      RoleEntity roleEntity = role.get();
      if (roleIdList.contains(roleEntity.getId())) {
        continue;
      }

      AccountRoleEntity accountRoleEntity =
          new AccountRoleEntity().setRole(roleEntity).setAccount(account);
      account.getRoles().add(accountRoleEntity);
    }

    account
        .setName(request.getName())
        .setLastName(request.getLastName())
        .setEmail(request.getEmail())
        .setPhoneNumber(request.getPhoneNumber())
        .setLastLogin(ZonedDateTime.now());

    return repository.save(account);
  }

  public AccountEntity update(AccountEntity account) {
    return repository.save(account);
  }

  public void updatePassword(String newPassword, Long id) {
    AccountEntity account = findById(id);
    if (account == null) {
      throw ApiException.bad(ERROR_PREFIX + "dont.exist");
    }
    account.setPassword(encoder.encode(newPassword));
    repository.save(account);
  }

  public void delete(Long id) {
    AccountEntity account = findById(id);
    if (account == null) {
      throw ApiException.bad(ERROR_PREFIX + "dont.exist");
    }
    repository.delete(account);
  }

  public AccountEntity updateAccountInfo(UpdateAccountInfoRequest request, Long accountId) {
    AccountEntity account = findById(accountId);
    account.setName(request.getName());
    account.setLastName(request.getLastName());
    account.setPhoneNumber(request.getPhoneNumber());
    if (!Objects.equals(account.getEmail(), request.getEmail())) {
      account.setIsEmailConfirmed(false);
      account.setEmail(request.getEmail());
      String uuid = UUID.randomUUID().toString();
      ZonedDateTime expirationDate = ZonedDateTime.now().plusDays(7);
      validationService.create(ValidationType.MAGIC_UUID, uuid, account.getEmail(), expirationDate);
    }

    return repository.save(account);
  }
}
