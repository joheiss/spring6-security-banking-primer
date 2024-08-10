package com.jovisco.springsecurity.primer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovisco.springsecurity.primer.dtos.LoansResponseDto;
import com.jovisco.springsecurity.primer.services.LoansService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoansController {

  private final LoansService loansService;

  @GetMapping("/myLoans")
  public List<LoansResponseDto> getLoanDetails(@RequestParam long id) {
    var loans = loansService.listLoans(id);
    return loans.stream()
        .map(loan -> new LoansResponseDto(
            loan.getLoanNumber(),
            loan.getCustomerId(),
            loan.getStartDt(),
            loan.getLoanType(),
            loan.getTotalLoan(),
            loan.getAmountPaid(),
            loan.getOutstandingAmount(),
            loan.getCreateDt()))
        .toList();
  }
}