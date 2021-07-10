package com.vvs.mainbackend.service;

import java.time.Instant;
import java.util.List;

import com.vvs.mainbackend.exception.PostNotFoundException;
import com.vvs.mainbackend.model.Post;
import com.vvs.mainbackend.pojo.PostDto;
import com.vvs.mainbackend.repository.PostRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.java.Log;

import static java.util.stream.Collectors.toList;

@Log
@Service
public class PostService {
  
  @Autowired
  private PostRepository postRepository;

  @Transactional
  public List<PostDto> showAllPosts() {
    List<Post> posts = postRepository.findAll();
    return posts.stream().map(this::mapFromPostToDto).collect(toList());
  }

  @Transactional
  public void createPost(PostDto postDto) {
    Post post = mapFromDtoToPost(postDto);
    postRepository.save(post);
  }

  @Transactional
  public PostDto readSinglPost(ObjectId id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostNotFoundException("Post not found for id: " + id));
    return mapFromPostToDto(post);
  }

  @Transactional
  public void updatePost(PostDto postDto) {
    Post post = postRepository.findById(postDto.getId()).orElseThrow(() -> new PostNotFoundException("Post not found."));
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setUsername(postDto.getUsername());
    post.setCreateOn(post.getCreateOn());
    post.setUpdateOn(Instant.now());
    postRepository.save(post);
  }

  private Post mapFromDtoToPost(PostDto postDto) {
    Post post = new Post();
    post.setId(postDto.getId());
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setUsername(postDto.getUsername());
    post.setCreateOn(Instant.now());
    post.setUpdateOn(Instant.now());
    return post;
  }

  private PostDto mapFromPostToDto(Post post) {
    PostDto postDto = new PostDto();
    postDto.setId(post.getId());
    postDto.set_id(post.getId().toHexString());
    postDto.setTitle(post.getTitle());
    postDto.setContent(post.getContent());
    postDto.setUsername(post.getUsername());
    postDto.setCreateOn(post.getCreateOn());
    postDto.setUpdateOn(post.getUpdateOn());
    return postDto;
  }
}
