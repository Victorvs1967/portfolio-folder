package com.vvs.fullregistrationwithmail.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.vvs.fullregistrationwithmail.model.ConfirmationToken;
import com.vvs.fullregistrationwithmail.repository.ConfirmationTokenReposotory;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
  
  private final ConfirmationTokenReposotory confirmationTokenReposotory;

  public void saveConfirmationToken(ConfirmationToken token) {
    confirmationTokenReposotory.save(token);
  }

  public Optional<ConfirmationToken> getToken(String token) {
    return confirmationTokenReposotory.findByToken(token);
  }

  public ConfirmationToken setConfirmedAt(String token) {
    ConfirmationToken confirmationToken = confirmationTokenReposotory.findByToken(token).orElseThrow();
    confirmationToken.setConfirmedAt(LocalDateTime.now());
    return confirmationTokenReposotory.save(confirmationToken);
    // return confirmationTokenReposotory.updateConfirmedAt(token, LocalDateTime.now());
  }
  
}
