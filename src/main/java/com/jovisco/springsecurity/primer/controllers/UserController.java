package com.jovisco.springsecurity.primer.controllers;

import org.springframework.http.HttpStatus;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.ApplicationConstants;
import com.jovisco.springsecurity.primer.dtos.CustomerCreateDto;
import com.jovisco.springsecurity.primer.dtos.LoginRequestDto;
import com.jovisco.springsecurity.primer.dtos.LoginResponseDto;
import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;
import com.jovisco.springsecurity.primer.services.LoginService;
import com.jovisco.springsecurity.primer.services.RegistrationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final RegistrationService registrationService;
    private final LoginService loginService;
    private final CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody CustomerCreateDto customerDto) {

        try {
            var savedCustomer = registrationService.registerUser(customerDto);
            return (savedCustomer.getId() > 0)
                    ? ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body("Given user details are successfully registered")
                    : ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body("User registration failed");
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred: " + ex.getMessage());
        }
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequest) {

        log.debug("*** apiLogin ***");

        var jwt = loginService.login(loginRequest.username(), loginRequest.password());

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(ApplicationConstants.JWT_HEADER, jwt)
                .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), jwt));
    }
}
