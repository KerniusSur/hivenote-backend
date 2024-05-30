package com.hivenote.backend.auth.controller;

import com.hivenote.backend.account.AccountService;
import com.hivenote.backend.account.entity.AccountEntity;
import com.hivenote.backend.auth.AuthService;
import com.hivenote.backend.auth.dto.request.EmailPasswordLoginRequest;
import com.hivenote.backend.auth.dto.request.RegisterConfirmationRequest;
import com.hivenote.backend.auth.dto.request.RegisterRequest;
import com.hivenote.backend.auth.dto.request.UpdatePasswordRequest;
import com.hivenote.backend.auth.dto.response.MeResponse;
import com.hivenote.backend.auth.entity.AuthenticatedProfile;
import com.hivenote.backend.auth.mapper.AuthMapper;
import com.hivenote.backend.validation.ValidationService;
import com.hivenote.backend.validation.entity.ValidationType;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api/v1/public/auth")
public class AuthController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private final AuthService authService;
  private final AccountService accountService;

  private final ValidationService validationService;

  public AuthController(
      AuthService authService, AccountService accountService, ValidationService validationService) {
    this.authService = authService;
    this.accountService = accountService;
    this.validationService = validationService;
  }

  @PostMapping("/login")
  public void login(@Valid @RequestBody EmailPasswordLoginRequest request, HttpServletResponse response) {
    LOGGER.info("Login request: email = [{}]", request.getEmail());
    authService.login(request, response);
  }

  @PostMapping("/logout")
  public void logout(HttpServletResponse response) {
    authService.logout(response);
  }

  @PostMapping("/register")
  public void register(@Valid @RequestBody RegisterRequest request, HttpServletResponse response) {
    LOGGER.info("Register request: email = [{}]", request.getEmail());
    authService.register(request, response);
  }

  @PutMapping("/register/confirm")
  public void registerConfirmation(
          @RequestBody RegisterConfirmationRequest request, AuthenticatedProfile profile) {
    LOGGER.info("Register confirmation request account id = [{}]", profile.getId());
    //    authService.confirmRegister(request, profile.getId());
  }

  @GetMapping(path = "/login/magic/send-email")
  public void sendEmailConfirmation(@RequestParam @Email String email) {
    LOGGER.info("Send magic link email request: email = [{}]", email);
    String uuid = UUID.randomUUID().toString();
    //    authNotificationUtil.sendMagicLinkEmail(email, uuid, false, null);
    validationService.create(ValidationType.MAGIC_UUID, uuid, email, null);
  }

  @PutMapping("/password/change")
  public void changePassword(
          @Valid @RequestBody UpdatePasswordRequest request, AuthenticatedProfile profile) {
    LOGGER.info("Change password request: account id = [{}]", profile.getId());
    //    authService.changePassword(request, profile.getId());
  }

  @GetMapping("/me")
  public MeResponse getMe(AuthenticatedProfile profile) {
    if (profile == null) {
      return null;
    }

    AccountEntity account = accountService.findByIdOrNull(profile.getId());
    if (account == null) {
      return null;
    }

    return AuthMapper.toResponse(account);
  }
}
