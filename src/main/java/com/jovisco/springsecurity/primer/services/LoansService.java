package com.jovisco.springsecurity.primer.services;

import java.util.List;

import com.jovisco.springsecurity.primer.entities.Loans;

public interface LoansService {
  List<Loans> listLoans(Long id);
}
