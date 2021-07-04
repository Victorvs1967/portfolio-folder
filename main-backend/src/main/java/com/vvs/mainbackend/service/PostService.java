package com.vvs.mainbackend.service;

import com.vvs.mainbackend.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  
  @Autowired
  private PostRepository postRepository;
}
