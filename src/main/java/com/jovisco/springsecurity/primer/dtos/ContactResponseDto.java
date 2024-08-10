package com.jovisco.springsecurity.primer.dtos;

import java.sql.Date;

public record ContactResponseDto(
    String contactId,
    String contactName,
    String contactEmail,
    String subject,
    String message,
    Date createDt) {
}
