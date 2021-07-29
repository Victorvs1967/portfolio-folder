package com.vvs.fullregistrationwithmail.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmationToken {
  
  private String token;
  private LocalDateTime create;
  private LocalDateTime expire;

}
