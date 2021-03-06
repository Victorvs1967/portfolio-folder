package com.vvs.fullregistrationwithmail.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vvs.fullregistrationwithmail.model.ConfirmationToken;
import com.vvs.fullregistrationwithmail.model.User;
import com.vvs.fullregistrationwithmail.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

  private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final ConfirmationTokenService confirmationTokenService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
  }

  public String signUpUser(User user) {
    boolean userExist = userRepository.findByEmail(user.getEmail()).isPresent();

    if (userExist) {
      // TODO: check of attributes are the same and if email not confirmed send confirmation email
      throw new IllegalStateException("email already taken");
    }

    String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
    user.setPassword(encodePassword);
    userRepository.save(user);

    String token = UUID.randomUUID().toString();

    ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(20), user);
    confirmationTokenService.saveConfirmationToken(confirmationToken);
    // TODO: Send email
    return token;
  }

  public User enableUser(String email) {
    User user = userRepository.findByEmail(email).orElseThrow();
    user.setEnable(true);
    return userRepository.save(user);
  }
  
}
