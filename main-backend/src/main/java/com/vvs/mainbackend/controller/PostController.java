package com.vvs.mainbackend.controller;

import java.util.List;

import com.vvs.mainbackend.pojo.PostDto;
import com.vvs.mainbackend.service.PostService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/posts")
public class PostController {
  
  @Autowired
  private PostService postService;

  @PostMapping
  public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
    postService.createPost(postDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<PostDto>> showAllPosts() {
    return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<PostDto> getSinglPost(@PathVariable ObjectId id) {
    return new ResponseEntity<>(postService.readSinglPost(id), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> editPost(@RequestBody PostDto postDto) {
    postService.updatePost(postDto);
    return ResponseEntity.ok(HttpStatus.OK);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<?> deletePost(@PathVariable ObjectId id) {
    this.postService.deletePost(id);
    return ResponseEntity.ok(HttpStatus.OK);
  }

}
