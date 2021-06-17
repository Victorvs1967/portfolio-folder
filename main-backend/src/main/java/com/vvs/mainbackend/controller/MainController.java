package com.vvs.mainbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/main")
public class MainController {
  
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

}
