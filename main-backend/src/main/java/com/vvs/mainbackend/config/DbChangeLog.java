package com.vvs.mainbackend.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.vvs.mainbackend.model.EnRole;
import com.vvs.mainbackend.model.Role;
import com.vvs.mainbackend.model.User;
import com.vvs.mainbackend.repository.RoleRepository;
import com.vvs.mainbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ChangeLog
public class DbChangeLog {

  @Value("${app.admin.username}")
  private String username;

  @Value("${app.admin.email}")
  private String email;

  @Value("${app.admin.password}")
  private String password;

  private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @ChangeSet(order = "001", id = "mainDB", author= "Me")
  public void mainDB(RoleRepository roleRepository, UserRepository userRepository) {

    List<Role> roles = new ArrayList<>();
    roles.add(addRole(EnRole.ROLE_ADMIN));
    roles.add(addRole(EnRole.ROLE_MODERATOR));
    roles.add(addRole(EnRole.ROLE_USER));
    roleRepository.insert(roles);

    Set<Role> adminRoles = new HashSet<>();
    Role adminRole = roleRepository.findByName(EnRole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("ERROR: Role ADMIN is not found"));
    adminRoles.add(adminRole);

    User admin = addAdmin(adminRoles);
    userRepository.insert(admin);
  }

  private Role addRole(EnRole roleName) {
    Role role = new Role();
    role.setName(roleName);
    return role;
  }

  private User addAdmin(Set<Role> roleNames) {
    String adminName = username;
    String adminPassword = password;
    String adminEmail = email;
    String pass = passwordEncoder().encode(adminPassword);
    User user = new User(adminName, pass, adminEmail);
    user.setRoles(roleNames);
    return user;
  }

}
