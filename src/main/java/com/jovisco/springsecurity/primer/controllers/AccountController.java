package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  @GetMapping("/myAccount")
  public String getAccountDetails() {
    return "my account details ...";
  }
}
