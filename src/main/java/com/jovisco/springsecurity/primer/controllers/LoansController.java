package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

  @GetMapping("/myLoans")
  public String getLoans() {
    return "my loans ...";
  }
}
