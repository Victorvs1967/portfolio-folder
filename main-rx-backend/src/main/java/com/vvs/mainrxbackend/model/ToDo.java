package com.vvs.mainrxbackend.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Document("todo")
public class ToDo {

  @Id
  private ObjectId id;
  private String description;
  private LocalDateTime created;
  private LocalDateTime modified;
  private boolean completed;

  public ToDo() {
    this.created = LocalDateTime.now();
    this.modified = LocalDateTime.now();
  }

  public ToDo(String description) {
    this();
    this.description = description;
  }

  public ToDo(String description, boolean completed) {
    this();
    this.description = description;
    this.completed = completed;
  }
  
}
