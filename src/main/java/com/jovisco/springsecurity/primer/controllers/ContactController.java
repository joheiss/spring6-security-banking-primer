package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

  @GetMapping("/contact")
  public String saveContactInquiryDetails() {
    return "save contact inquiry details ...";
  }
}
