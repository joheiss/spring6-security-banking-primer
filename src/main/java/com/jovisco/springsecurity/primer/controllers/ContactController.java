package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.dtos.ContactRequestDto;
import com.jovisco.springsecurity.primer.dtos.ContactResponseDto;
import com.jovisco.springsecurity.primer.services.ContactService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ContactController {

  private final ContactService contactService;

  @GetMapping("/contact")
  public List<ContactResponseDto> listContacts() {
    return contactService.listContacts();
  }

  @PostMapping("/contact")
  public List<ContactResponseDto> saveContactInquiryDetails(@RequestBody List<ContactRequestDto> contactRequestDtos) {

    return contactService.saveContacts(contactRequestDtos);
  }
}