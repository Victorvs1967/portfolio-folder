package com.vvs.mainbackend.pojo;

import org.bson.types.ObjectId;

import lombok.Data;

@Data
public class PostDto {
  
  private ObjectId id;
  private String content;
  private String title;
  private String username;
}
