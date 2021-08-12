package com.vvs.mainrxbackend.service;

import com.vvs.mainrxbackend.model.Message;
import com.vvs.mainrxbackend.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {
  
  @Autowired
  private MessageRepository messageRepository;

  public Flux<Message> list() {
    return messageRepository.findAll();
  }

  public Mono<Message> addOne(Message message) {
    return messageRepository.save(message);
  }
}
