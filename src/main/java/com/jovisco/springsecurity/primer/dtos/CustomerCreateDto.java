package com.jovisco.springsecurity.primer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerCreateDto {

    private String name;
    private String email;
    private String mobileNumber;
    private String pwd;
    private String role;
}
