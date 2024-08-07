package com.jovisco.springsecurity.primer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jovisco.springsecurity.primer.entities.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
}
