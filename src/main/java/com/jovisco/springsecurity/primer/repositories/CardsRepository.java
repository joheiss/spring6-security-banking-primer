package com.jovisco.springsecurity.primer.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jovisco.springsecurity.primer.entities.Cards;


@Repository
public interface CardsRepository extends CrudRepository<Cards, Long> {
	
	List<Cards> findByCustomerId(long customerId);
}