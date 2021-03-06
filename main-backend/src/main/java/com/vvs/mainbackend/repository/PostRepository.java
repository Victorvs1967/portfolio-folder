package com.vvs.mainbackend.repository;

import java.util.Optional;

import com.vvs.mainbackend.model.Post;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, ObjectId> {
  Optional<Post> findByTitle(String title); 
  Boolean existsByTitle(String title);

}
