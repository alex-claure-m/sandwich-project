package com.example.sandwich.services.mail.models;

import lombok.*;


@Getter
@Setter
public class EmailDetailsDTO {
  // Class data members
  private String recipient;
  private String msgBody;
  private String subject;
  private String attachment;
}
