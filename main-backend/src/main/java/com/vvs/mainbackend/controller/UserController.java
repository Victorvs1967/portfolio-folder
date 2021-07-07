package com.vvs.mainbackend.controller;

import java.util.Set;

import com.vvs.mainbackend.model.Role;
import com.vvs.mainbackend.model.User;
import com.vvs.mainbackend.pojo.MessageResponse;
import com.vvs.mainbackend.pojo.SignupRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;
  
  @GetMapping("/users")
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> getAllUsers() {
    return userRepository.findAll();
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
