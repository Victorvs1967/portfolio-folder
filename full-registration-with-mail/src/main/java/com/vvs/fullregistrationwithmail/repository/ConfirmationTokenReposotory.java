package com.vvs.fullregistrationwithmail.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import com.vvs.fullregistrationwithmail.model.ConfirmationToken;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenReposotory extends MongoRepository<ConfirmationToken, ObjectId> {
  Optional<ConfirmationToken> findByToken(String token);

  @Transactional
  @Query("UPDATE ConfirmationToken c " +
          "SET c.confirmadAt = ?2 " +
          "WHERE c.token = ?1")
  int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
