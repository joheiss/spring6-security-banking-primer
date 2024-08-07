package com.jovisco.springsecurity.primer.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.AccountTransactions;
import com.jovisco.springsecurity.primer.repositories.AccountTransactionsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BalanceController {

  private final AccountTransactionsRepository accountTransactionsRepository;

  @GetMapping("/myBalance")
  public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
    List<AccountTransactions> accountTransactions = accountTransactionsRepository
        .findByCustomerIdOrderByTransactionDtDesc(id);
    return (accountTransactions != null) ? accountTransactions : null;
  }
}
