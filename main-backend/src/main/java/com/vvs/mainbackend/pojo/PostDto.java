package com.vvs.mainbackend.pojo;

import java.time.Instant;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class PostDto {
  
  private ObjectId id;
  private String content;
  private String title;
  private String username;
  private Instant createOn;
  private Instant updateOn;
  private String _id = id.toHexString();
  
}
