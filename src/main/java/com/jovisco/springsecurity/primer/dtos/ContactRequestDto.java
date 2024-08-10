package com.jovisco.springsecurity.primer.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ContactRequestDto {
  private String contactId;
  private String contactName;
  private String contactEmail;
  private String subject;
  private String message;
}
