package com.vvs.fullregistrationwithmail.repository;

import java.util.Optional;

import com.vvs.fullregistrationwithmail.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
  Optional<User> findByEmail(String email);

  @Transactional
  @Query(
    "UPDATE User a" +
    "SET a.enabled = TRUE WHERE a.email = ?1"
  )
  int enableUser(String email);
}
