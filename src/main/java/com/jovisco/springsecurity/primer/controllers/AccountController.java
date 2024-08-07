package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Accounts;
import com.jovisco.springsecurity.primer.repositories.AccountsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {

        Accounts accounts = accountsRepository.findByCustomerId(id);
        return (accounts != null) ? accounts : null;
    }
}
