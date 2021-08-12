package com.vvs.mainrxbackend.repository;

import com.vvs.mainrxbackend.model.Message;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, ObjectId> {
  
}
