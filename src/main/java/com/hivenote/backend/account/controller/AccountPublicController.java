package com.hivenote.backend.account.controller;

import com.hivenote.backend.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/api/v1/public/account")
public class AccountPublicController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AccountPublicController.class);

  private final AccountService service;

  public AccountPublicController(AccountService accountService) {
    this.service = accountService;
  }
}
