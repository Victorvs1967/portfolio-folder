package com.vvs.fullregistrationwithmail.controller;

import com.vvs.fullregistrationwithmail.model.RegistrationRequest;
import com.vvs.fullregistrationwithmail.service.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {
  
  @Autowired
  RegistrationService registrationService;

  @PostMapping
  public String register(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }

  @GetMapping("confirm")
  public String confirm(@RequestParam("token") String token) {
    return registrationService.confirmedToken(token);
  }
}
