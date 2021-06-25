package com.vvs.mainbackend.service;

import com.vvs.mainbackend.model.User;
import com.vvs.mainbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User delete(String username) {
    User user = userRepository.findByUsername(username).orElseThrow();
    userRepository.delete(user);
    return user;
  }

}
