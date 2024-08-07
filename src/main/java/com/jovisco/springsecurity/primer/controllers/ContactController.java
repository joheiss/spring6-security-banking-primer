package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Contact;
import com.jovisco.springsecurity.primer.repositories.ContactRepository;

import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.util.Random;

@RequiredArgsConstructor

@RestController
public class ContactController {

  private final ContactRepository contactRepository;

  @PostMapping("/contact")
  public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
    contact.setContactId(getServiceReqNumber());
    contact.setCreateDt(new Date(System.currentTimeMillis()));
    return contactRepository.save(contact);
  }

  public String getServiceReqNumber() {
    Random random = new Random();
    int ranNum = random.nextInt(999999999 - 9999) + 9999;
    return "SR" + ranNum;
  }
}