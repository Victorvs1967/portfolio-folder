package com.vvs.mainbackend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vvs.mainbackend.model.EnRole;
import com.vvs.mainbackend.model.Role;
import com.vvs.mainbackend.model.User;
import com.vvs.mainbackend.pojo.MessageResponse;
import com.vvs.mainbackend.pojo.SignupRequest;
import com.vvs.mainbackend.repository.UserRepository;
import com.vvs.mainbackend.repository.RoleRepository;
import com.vvs.mainbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@Log
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/main")
public class MainController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;
  
  @GetMapping("/hello")
  public String hello() {
    return "<h1>Hello my Friends!</h1>";
  }

  @GetMapping("/all")
  public String getAll() {
    return "<h2>Public API</h2>";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String getAdminApi() {
    return "<h2>Admin API</h2>";
  }

  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public String getUserApi() {
    return "<h2>User API</h2>";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
  public String getModApi() {
    return "<h2>Moderator API</h2>";
  }

  @DeleteMapping("/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public User deleteUser(@PathVariable String username) {
    return userService.delete(username);
  }

  @PutMapping("/{username}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> editUser(@RequestBody SignupRequest editedUser) {
    User user = userRepository.findByUsername(editedUser.getUsername()).orElseThrow();
    User newUser = new User(editedUser.getUsername(), editedUser.getPassword(), editedUser.getEmail());
    newUser.setId(user.getId());
    newUser.setFullname(editedUser.getFullname());

    Set<String> reqRoles = editedUser.getRoles();
    Set<Role> roles = new HashSet<>();

    if (reqRoles == null) {
      Role userRole = roleRepository.findByName(EnRole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("ERROR: Role USER is not found"));
      roles.add(userRole);
    } else {
      reqRoles.forEach(r -> {
        switch (r) {
          case "admin":
            Role adminRole = roleRepository.findByName(EnRole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("ERROR: Role ADMIN is not found"));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(EnRole.ROLE_MODERATOR)
                .orElseThrow(() -> new RuntimeException("ERROR: Role MODERATOR is not found"));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(EnRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ERROR: Role USER is not found"));
            roles.add(userRole);
        }
      });
    }

    newUser.setRoles(roles);
    userRepository.save(newUser);
    return ResponseEntity.ok(new MessageResponse("User Edited"));
  }

}
