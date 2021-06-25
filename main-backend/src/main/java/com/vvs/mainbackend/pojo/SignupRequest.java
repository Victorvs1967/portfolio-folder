package com.vvs.mainbackend.pojo;

import java.util.Set;

import lombok.Data;

@Data
public class SignupRequest {
  
  private String fullname;
  private String username;
  private String email;
  private String password;
  private Set<String> roles;

}
