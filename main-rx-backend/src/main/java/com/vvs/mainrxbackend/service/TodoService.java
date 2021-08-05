package com.vvs.mainrxbackend.service;

import com.vvs.mainrxbackend.model.ToDo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoService {
  Flux<ToDo> getAll();
  Mono<ToDo> saveOrUpdate(ToDo todo);
  void delete(String id);
}
