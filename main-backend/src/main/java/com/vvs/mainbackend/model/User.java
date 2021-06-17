package com.vvs.mainbackend.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document("user")
public class User {
  
  @Id
  private ObjectId id;
  @Field("username")
  @Indexed(unique = true)
  private String username;
  @Field("password")
  private String password;
  @Field("fullname")
  private String fullname;
  @Field("email")
  @Indexed(unique = true)
  private String email;
  @Field("roles")
  private Set<Role> roles = new HashSet<>();

  @DBRef
  private List<ObjectId> roleIds;

  public User(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }

}
