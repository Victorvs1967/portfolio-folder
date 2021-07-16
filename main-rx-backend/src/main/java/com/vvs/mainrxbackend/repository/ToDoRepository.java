package com.vvs.mainrxbackend.repository;

import com.vvs.mainrxbackend.model.ToDo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface ToDoRepository extends ReactiveMongoRepository<ToDo, ObjectId> {
}
