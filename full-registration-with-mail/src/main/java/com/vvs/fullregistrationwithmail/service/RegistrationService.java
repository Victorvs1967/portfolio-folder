package com.vvs.fullregistrationwithmail.service;

import com.vvs.fullregistrationwithmail.model.RegistrationRequest;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
  
  public String confirmToken(String token) {

    return token;
  }

  public String register(RegistrationRequest request) {
    return request.toString();
  }
}
