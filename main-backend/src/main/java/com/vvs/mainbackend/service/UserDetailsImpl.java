package com.vvs.mainbackend.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vvs.mainbackend.model.User;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private ObjectId id;
  private String username;
  private String email;
  @JsonIgnore
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public static UserDetailsImpl build(User user) {

    List<GrantedAuthority> authorities = user.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword(),
      authorities
    );
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
  
}
