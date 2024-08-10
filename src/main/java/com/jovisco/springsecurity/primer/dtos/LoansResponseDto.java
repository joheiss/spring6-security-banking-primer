package com.jovisco.springsecurity.primer.dtos;

import java.sql.Date;

public record LoansResponseDto(
    long loanNumber,
    long customerId,
    Date startDt,
    String loanType,
    int totalLoan,
    int amountPaid,
    int outstandingAmount,
    Date createDt) {
}
