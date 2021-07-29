package com.vvs.fullregistrationwithmail.repository;

import java.util.Optional;

import com.vvs.fullregistrationwithmail.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends MongoRepository<User, ObjectId> {
  Optional<User> findByEmail(String email);
}
