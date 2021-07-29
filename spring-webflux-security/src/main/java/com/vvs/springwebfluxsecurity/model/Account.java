package com.vvs.springwebfluxsecurity.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document
public class Account {
  
  @Id
  private ObjectId _id;
  private String owner;
  private Double value;

  public Account(String owner, Double value) {
    this.owner = owner;
    this.value = value;
  }

}
