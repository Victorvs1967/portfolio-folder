package com.vvs.mainbackend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vvs.mainbackend.jwt.JwtUtils;
import com.vvs.mainbackend.model.EnRole;
import com.vvs.mainbackend.model.Role;
import com.vvs.mainbackend.model.User;
import com.vvs.mainbackend.pojo.JwtResponse;
import com.vvs.mainbackend.pojo.LoginRequest;
import com.vvs.mainbackend.pojo.MessageResponse;
import com.vvs.mainbackend.pojo.SignupRequest;
import com.vvs.mainbackend.repository.RoleRepository;
import com.vvs.mainbackend.repository.UserRepository;
import com.vvs.mainbackend.service.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
  
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;
  
  @Autowired
  JwtUtils jwtUtil;

  @PostMapping("/signin")
  public ResponseEntity<?> authUser(@RequestBody LoginRequest loginRequest) {
    
    Authentication auth = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      ));
    SecurityContextHolder.getContext().setAuthentication(auth);
    String jwt = jwtUtil.generateJwtToken(auth);

    UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    List<String> roles = userDetails.getAuthorities()
      .stream()
      .map(item -> item.getAuthority())
      .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(
      jwt,
      userDetails.getId(),
      userDetails.getUsername(),
      userDetails.getEmail(),
      roles
    ));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
    
    if (userRepository.existsByUsername(signupRequest.getUsername())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("ERROR: Username is exist"));
    }
    if (userRepository.existsByEmail(signupRequest.getEmail())) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("ERROR: Email is exist"));
    }

    User user = new User(
        signupRequest.getUsername(),
        passwordEncoder.encode(signupRequest.getPassword()),
        signupRequest.getEmail());

    Set<String> reqRoles = signupRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (reqRoles == null) {
      Role userRole = roleRepository.findByName(EnRole.ROLE_USER).orElseThrow(() -> new RuntimeException("ERROR: Role USER is not found"));
      roles.add(userRole);
    } else {
      reqRoles.forEach(r -> {
        switch (r) {
          case "admin":
            Role adminRole = roleRepository.findByName(EnRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("ERROR: Role ADMIN is not found"));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(EnRole.ROLE_MODERATOR).orElseThrow(() -> new RuntimeException("ERROR: Role MODERATOR is not found"));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(EnRole.ROLE_USER).orElseThrow(() -> new RuntimeException("ERROR: Role USER is not found"));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User Created"));
  }
}
