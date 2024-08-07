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
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        customer.setRole(customerDto.getRole());
        var hashPwd = passwordEncoder.encode(customerDto.getPwd());
        customer.setPwd(hashPwd);
        customer.setCreateDt(new java.sql.Date(System.currentTimeMillis()));
        var savedCustomer = customerRepository.save(customer);

        return savedCustomer;
    }

}
