package com.vvs.mainrxbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document("messages")
public class Message {
  

  @Id
  private Long id;
  private String data;

  public Message(String data) {
    this.data = data;
  }
}
