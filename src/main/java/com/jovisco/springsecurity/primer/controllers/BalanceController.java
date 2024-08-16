package com.jovisco.springsecurity.primer.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.AccountTransactions;
import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.AccountTransactionsRepository;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BalanceController {

  private final CustomerRepository customerRepository;
  private final AccountTransactionsRepository accountTransactionsRepository;

  @GetMapping("/myBalance")
  public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {

    Optional<Customer> customer = customerRepository.findByEmail(email);
    if (customer.isPresent()) {
      return accountTransactionsRepository
          .findByCustomerIdOrderByTransactionDtDesc(customer.get().getId());
    }
    return null;
  }
}
