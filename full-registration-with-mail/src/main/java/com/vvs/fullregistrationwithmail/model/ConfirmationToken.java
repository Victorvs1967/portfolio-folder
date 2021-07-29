package com.vvs.fullregistrationwithmail.model;

import java.time.LocalDateTime;

import com.mongodb.lang.NonNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("tokens")
public class ConfirmationToken {
  
  @Id
  private ObjectId id;
  @NonNull
  private String token;
  @NonNull
  private LocalDateTime createdAt;
  @NonNull
  private LocalDateTime expiresAt;
  private LocalDateTime confirmedAt;

  @DBRef
  private User user;

  public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.user = user;
  }

}
