package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Loans;
import com.jovisco.springsecurity.primer.repositories.LoansRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoansController {

  private final LoansRepository loansRepository;

  @GetMapping("/myLoans")
  public List<Loans> getLoanDetails(@RequestParam long id) {
    return loansRepository.findByCustomerIdOrderByStartDtDesc(id);
  }
}