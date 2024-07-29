package com.jovisco.springsecurity.primer.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.dtos.CustomerCreateDto;
import com.jovisco.springsecurity.primer.entities.Customer;
import com.jovisco.springsecurity.primer.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    @Override
    public Customer registerUser(CustomerCreateDto customerDto) {

        var customer = new Customer();
        customer.setEmail(customerDto.getEmail());
        customer.setRole(customerDto.getRole());
        var hashPwd = passwordEncoder.encode(customerDto.getPwd());
        customer.setPwd(hashPwd);
        var savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }

}
