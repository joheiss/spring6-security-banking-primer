package com.jovisco.springsecurity.primer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.dtos.CustomerCreateDto;
import com.jovisco.springsecurity.primer.services.RegistrationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CustomerCreateDto customerDto) {

        try {
                var savedCustomer = registrationService.registerUser(customerDto);

                if (savedCustomer.getId() > 0) {
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
