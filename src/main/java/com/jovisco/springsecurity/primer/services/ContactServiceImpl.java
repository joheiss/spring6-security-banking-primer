package com.jovisco.springsecurity.primer.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.dtos.ContactRequestDto;
import com.jovisco.springsecurity.primer.dtos.ContactResponseDto;
import com.jovisco.springsecurity.primer.entities.Contact;
import com.jovisco.springsecurity.primer.mappers.ContactMapper;
import com.jovisco.springsecurity.primer.repositories.ContactRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactServiceImpl implements ContactService {

  private final ContactRepository contactRepository;

  @Override
  @PreFilter("filterObject.contactName != 'Test'")
  public List<ContactResponseDto> saveContacts(List<ContactRequestDto> contacts) {

    List<ContactResponseDto> savedContacts = new ArrayList<ContactResponseDto>();

    log.debug("Contact to be saved: {}", contacts);

    if (!contacts.isEmpty()) {
      List<Contact> toBeSaved = new ArrayList<Contact>();
      toBeSaved = contacts.stream()
          .map(contactRequestDto -> {
            var contact = ContactMapper.requestDtoToEntity(contactRequestDto);
            contact.setContactId(getServiceReqNumber());
            contact.setCreateDt(new Date(System.currentTimeMillis()));
            return contact;
          })
          .toList();

      var saved = contactRepository.saveAll(toBeSaved);
      List<Contact> savedList = new ArrayList<Contact>();
      saved.forEach(savedList::add);
      savedContacts = savedList.stream()
          .map(ContactMapper::entityToResponseDto)
          .toList();
    }
    return savedContacts;
  }

  private String getServiceReqNumber() {
    Random random = new Random();
    int ranNum = random.nextInt(999999999 - 9999) + 9999;
    return "SR" + ranNum;
  }

  @Override
  public List<ContactResponseDto> listContacts() {
    List<ContactResponseDto> list = new ArrayList<>();
    var contacts = contactRepository.findAll();
    for (Contact contact : contacts) {
      list.add(ContactMapper.entityToResponseDto(contact));
    }
    return list;
  }

}
