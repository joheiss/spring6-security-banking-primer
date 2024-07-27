package com.jovisco.springsecurity.primer.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public Customer registerUser(Customer customer) {

        var hashPwd = passwordEncoder.encode(customer.getPwd());
        customer.setPwd(hashPwd);
        var savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }

}
