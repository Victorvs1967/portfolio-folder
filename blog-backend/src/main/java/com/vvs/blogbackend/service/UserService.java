package com.vvs.blogbackend.service;

import java.util.List;
import java.util.UUID;

import com.vvs.blogbackend.model.User;
import com.vvs.blogbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRepository;

  public List<User> list() {
    return userRepository.findAll();
  }

  public User save(User user) {
    if (user.getId() == null) user.setId(UUID.randomUUID()); 
    return userRepository.save(user);
  }

  public UUID remove(User user) {
    UUID deletedId = user.getId();
    if (deletedId != null) userRepository.deleteById(deletedId);
    return deletedId;

  }
}
