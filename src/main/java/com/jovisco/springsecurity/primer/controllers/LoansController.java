package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.dtos.LoansResponseDto;
import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;
import com.jovisco.springsecurity.primer.services.LoansService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LoansController {

  private final CustomerRepository customerRepository;
  private final LoansService loansService;

  @GetMapping("/myLoans")
  public List<LoansResponseDto> getLoanDetails(@RequestParam String email) {

    Optional<Customer> customer = customerRepository.findByEmail(email);
    if (customer.isPresent()) {
      var loans = loansService.listLoans(customer.get().getId());
      return loans.stream()
          .map(loan -> new LoansResponseDto(
              loan.getLoanNumber(),
              loan.getCustomerId(),
              loan.getStartDt(),
              loan.getLoanType(),
              loan.getTotalLoan(),
              loan.getAmountPaid(),
              loan.getOutstandingAmount(),
              loan.getCreateDt()))
          .toList();
    }
    return null;
  }
}