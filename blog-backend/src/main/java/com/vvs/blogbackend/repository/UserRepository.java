package com.vvs.blogbackend.repository;

import java.util.UUID;

import com.vvs.blogbackend.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, UUID> {
  
}
