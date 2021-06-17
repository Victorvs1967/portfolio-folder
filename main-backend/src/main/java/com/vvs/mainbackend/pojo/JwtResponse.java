package com.vvs.mainbackend.pojo;

import java.util.List;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
  
  private String token;
  private String type = "Bearer";
  private ObjectId id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String token, ObjectId id, String username, String email, List<String> roles) {
    this.token = token;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
