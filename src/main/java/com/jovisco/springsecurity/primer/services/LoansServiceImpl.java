package com.jovisco.springsecurity.primer.services;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.jovisco.springsecurity.primer.entities.Loans;
import com.jovisco.springsecurity.primer.repositories.LoansRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoansServiceImpl implements LoansService {

  private final LoansRepository loansRepository;

  @Override
  @PreAuthorize("hasRole('USER')")
  public List<Loans> listLoans(Long id) {
    return loansRepository.findByCustomerIdOrderByStartDtDesc(id);
  }

}
