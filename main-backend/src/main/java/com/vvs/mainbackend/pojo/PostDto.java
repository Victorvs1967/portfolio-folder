package com.vvs.mainbackend.pojo;

import java.time.Instant;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class PostDto {
  
  private ObjectId id;
  private String _id;
  private String content;
  private String title;
  private String username;
  private Instant createOn;
  private Instant updateOn;

  public void set_id() {
    _id = id.toHexString();
  }

  public String get_id() {
    return _id;
  }
  
}
