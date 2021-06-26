package com.vvs.mainbackend.pojo;

import java.util.Set;

import com.vvs.mainbackend.model.Role;

import lombok.Data;

@Data
public class EditedUser {
  
  private String fullname;
  private String username;
  private String email;
  private String password;
  private Set<Role> roles;

}
