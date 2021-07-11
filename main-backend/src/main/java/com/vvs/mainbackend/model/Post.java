package com.vvs.mainbackend.model;

import java.time.Instant;

import com.github.cloudyrock.mongock.utils.field.Field;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document("post")
public class Post {
  
  @Id
  private ObjectId id;
  @Indexed
  @Field("title")
  private String title;
  @Field("content")
  private String content;
  @CreatedDate
  @Field("createOn")
  private Instant createOn;
  @LastModifiedDate
  @Field("updateOn")
  private Instant updateOn;
  @Indexed(unique = true)
  @Field("username")
  private String username;

}
