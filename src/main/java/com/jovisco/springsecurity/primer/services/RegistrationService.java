package com.jovisco.springsecurity.primer.services;

import com.jovisco.springsecurity.primer.dtos.CustomerCreateDto;
import com.jovisco.springsecurity.primer.entities.Customer;

public interface RegistrationService {

    Customer registerUser(CustomerCreateDto customerDto);
}
