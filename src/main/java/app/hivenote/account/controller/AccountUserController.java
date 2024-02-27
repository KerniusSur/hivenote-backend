package app.hivenote.account.controller;

import app.hivenote.account.AccountService;
import app.hivenote.account.dto.request.PasswordUpdateRequest;
import app.hivenote.account.dto.request.UpdateAccountInfoRequest;
import app.hivenote.auth.entity.AuthenticatedProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
@RequestMapping("/api/v1/user/account")
public class AccountUserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AccountUserController.class);
  private final AccountService service;

  public AccountUserController(AccountService accountService) {
    this.service = accountService;
  }

  @PutMapping("/password")
  public void updatePassword(
      @RequestBody PasswordUpdateRequest request, AuthenticatedProfile profile) {
    service.updatePassword(request.getNewPassword(), profile.getId());
  }

  @PutMapping
  public void updateAccountInfo(
      @RequestBody UpdateAccountInfoRequest request, AuthenticatedProfile profile) {
    service.updateAccountInfo(request, profile.getId());
  }
}
