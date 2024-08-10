package com.jovisco.springsecurity.primer.mappers;

import com.jovisco.springsecurity.primer.dtos.ContactRequestDto;
import com.jovisco.springsecurity.primer.dtos.ContactResponseDto;
import com.jovisco.springsecurity.primer.entities.Contact;

public class ContactMapper {

  static public Contact requestDtoToEntity(ContactRequestDto dto) {

    var contact = new Contact();
    contact.setContactName(dto.getContactName());
    contact.setContactEmail(dto.getContactEmail());
    contact.setSubject(dto.getSubject());
    contact.setMessage(dto.getMessage());
    return contact;
  }

  static public ContactResponseDto entityToResponseDto(Contact contact) {
    return new ContactResponseDto(
        contact.getContactId(),
        contact.getContactName(),
        contact.getContactEmail(),
        contact.getSubject(),
        contact.getMessage(),
        contact.getCreateDt());
  }
}
