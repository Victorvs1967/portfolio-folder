package com.vvs.fullregistrationwithmail.service;

public interface EmailSender {
  
  void send(String to, String email);
}
