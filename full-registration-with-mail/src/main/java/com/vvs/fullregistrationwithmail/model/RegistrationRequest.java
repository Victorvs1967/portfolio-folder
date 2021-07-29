package com.vvs.fullregistrationwithmail.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class RegistrationRequest {

  private final String firstName;
  private final String lastName;
  private final String email;
  private final String password;

}
