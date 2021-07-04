package com.vvs.mainbackend.controller;

import com.vvs.mainbackend.pojo.PostDto;
import com.vvs.mainbackend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/posts")
public class PostController {
  
  @Autowired
  private PostRepository postRepository;

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
    return ResponseEntity.ok(HttpStatus.OK);
  }
}
