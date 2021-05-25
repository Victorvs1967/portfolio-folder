package com.vvs.blogbackend.controller;

import java.util.List;
import java.util.UUID;

import com.vvs.blogbackend.model.User;
import com.vvs.blogbackend.repository.UserRepository;
import com.vvs.blogbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {
  
  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  public List<User> getUsers() {
    return userService.list();
  }

  @PostMapping
  public UUID addUser(@RequestBody User user) {
    return userService.save(user)
            .getId();
  }

  @DeleteMapping("{id}")
  public UUID remove(@PathVariable("id") UUID id) {
    User deletedUser = userRepository.findById(id)
          .orElse(new User());
    return userService.remove(deletedUser);
  }

}
