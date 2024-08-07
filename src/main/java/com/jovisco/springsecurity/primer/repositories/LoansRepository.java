package com.jovisco.springsecurity.primer.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.jovisco.springsecurity.primer.entities.Loans;

public interface LoansRepository extends CrudRepository<Loans, Long>{ 

  List<Loans> findByCustomerIdOrderByStartDtDesc(long customerId);
}
