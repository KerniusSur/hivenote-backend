package com.hivenote.backend.account.controller;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.dto.request.PasswordUpdateRequest;
import com.hivenote.backend.account.dto.request.UpdateAccountInfoRequest;
import com.hivenote.backend.auth.entity.AuthenticatedProfile;
import jakarta.validation.Valid;
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
      @Valid @RequestBody PasswordUpdateRequest request, AuthenticatedProfile profile) {
    service.updatePassword(request.getNewPassword(), profile.getId());
  }

  @PutMapping
  public void updateAccountInfo(
      @Valid @RequestBody UpdateAccountInfoRequest request, AuthenticatedProfile profile) {
    service.updateAccountInfo(request, profile.getId());
  }
}
