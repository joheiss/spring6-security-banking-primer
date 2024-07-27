package com.jovisco.springsecurity.primer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

private final CustomerRepository customerRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        
        try {
                var hashPwd = passwordEncoder.encode(customer.getPwd());
                customer.setPwd(hashPwd);
                var savedCustomer = customerRepository.save(customer);

                if(savedCustomer.getId() > 0) {
                    return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
                } else {
                    return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed");
                }
        } catch (Exception ex) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An exception occurred: " + ex.getMessage());
        }
    }
}
