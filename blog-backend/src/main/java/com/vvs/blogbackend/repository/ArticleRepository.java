package com.vvs.blogbackend.repository;

import java.util.UUID;

import com.vvs.blogbackend.model.Article;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArticleRepository extends MongoRepository<Article, UUID> {
  
}
