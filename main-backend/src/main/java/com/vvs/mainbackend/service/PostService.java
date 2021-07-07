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

import static java.util.stream.Collectors.toList;

@Service
public class PostService {
  
  @Autowired
  private AuthService authService;

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
    Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post not found for id: " + id));
    return mapFromPostToDto(post);
  }

  private Post mapFromDtoToPost(PostDto postDto) {
    Post post = new Post();
    post.setId(postDto.getId());
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    org.springframework.security.core.userdetails.User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User not found"));
    post.setCreateOn(Instant.now());
    post.setUsername(loggedInUser.getUsername());
    post.setUpdateOn(Instant.now());
    return post;
  }

  private PostDto mapFromPostToDto(Post post) {
    PostDto postDto = new PostDto();
    postDto.setId(post.getId());
    postDto.setTitle(post.getTitle());
    postDto.setContent(post.getContent());
    postDto.setUsername(post.getUsername());
    return postDto;
  }
}
