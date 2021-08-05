package com.vvs.mainrxbackend.service;

import com.vvs.mainrxbackend.model.ToDo;
import com.vvs.mainrxbackend.repository.ToDoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TodoServiceImpl implements TodoService {

  @Autowired
  ToDoRepository todoRepository;

  @Override
  public Flux<ToDo> getAll() {
    return todoRepository.findAll();
  }

  @Override
  public Mono<ToDo> saveOrUpdate(ToDo todo) {
    return todoRepository.save(todo);
  }

  @Override
  public void delete(String id) {
    todoRepository.findById(id).subscribe(todoRepository::delete);
  }
  
}
