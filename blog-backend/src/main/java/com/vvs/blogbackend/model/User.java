package com.vvs.blogbackend.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class User {
  
  @Id
  private UUID id;
  
  private String name;
  private String password;
  private String email;
  private Role role;
}
