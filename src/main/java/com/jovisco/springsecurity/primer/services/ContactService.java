package com.jovisco.springsecurity.primer.services;

import java.util.List;

import com.jovisco.springsecurity.primer.dtos.ContactRequestDto;
import com.jovisco.springsecurity.primer.dtos.ContactResponseDto;

public interface ContactService {
  List<ContactResponseDto> listContacts();

  List<ContactResponseDto> saveContacts(List<ContactRequestDto> contacts);

}
