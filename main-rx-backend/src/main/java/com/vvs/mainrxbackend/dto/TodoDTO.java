package com.vvs.mainrxbackend.dto;

import java.time.Instant;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class TodoDTO {
  
  private ObjectId id;
  private String _id;
  private String description;
  private Instant created;
  private Instant modified;
  private boolean completed;

  public void set_id() {
    _id = id.toHexString();
  }

  public String get_id() {
    return _id;
  }
}
