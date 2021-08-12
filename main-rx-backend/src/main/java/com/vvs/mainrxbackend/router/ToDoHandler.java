package com.vvs.mainrxbackend.router;

import com.vvs.mainrxbackend.config.JwtUtil;
import com.vvs.mainrxbackend.model.ToDo;
import com.vvs.mainrxbackend.model.User;
import com.vvs.mainrxbackend.repository.ToDoRepository;
import com.vvs.mainrxbackend.service.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.springframework.web.reactive.function.BodyInserters.*;

@Component
public class ToDoHandler {

  private final static ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  private final static ResponseEntity<Object> AUTHORIZED = ResponseEntity.status(HttpStatus.OK).build();

  @Autowired
  private ToDoRepository todoRepository;
  @Autowired
  private UsersService usersService;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<?> user = request.bodyToMono(User.class).flatMap(credentials -> usersService.findByUsername(credentials.getUsername()).cast(User.class)
                                                  .map(userDetails -> passwordEncoder.matches(credentials.getPassword(), userDetails.getPassword())
                                                      ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                                      : UNAUTHORIZED)
                                                  .defaultIfEmpty(UNAUTHORIZED));        
    return ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(user, User.class);
  }
  
  public Mono<ServerResponse> signup(ServerRequest request) {
    Mono<?> user = request.bodyToMono(User.class).flatMap(credentials -> usersService.registerUser(credentials.getUsername(), credentials.getPassword())
                                                    .cast(User.class)
                                                    .map(userDetails -> AUTHORIZED));
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(user, User.class);
  }
  
  public Mono<ServerResponse> getToDo(ServerRequest request) {
    return findById(request.pathVariable("id"));
  }

  public Mono<ServerResponse> getToDos(ServerRequest request) {
    Flux<ToDo> toDos = this.todoRepository.findAll();
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(toDos, ToDo.class);
  }

  public Mono<ServerResponse> newToDo(ServerRequest request) {
    Mono<ToDo> todo = request.bodyToMono(ToDo.class);
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromPublisher(todo.flatMap(this::save), ToDo.class));
  }

  public Mono<ServerResponse> updateToDo(ServerRequest request) {
    Mono<ToDo> todo = request.bodyToMono(ToDo.class);
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromPublisher(todo.flatMap(this::update), ToDo.class));
  }

  public Mono<ServerResponse> deleteTodo(ServerRequest request) {
    Mono<ToDo> todo = this.todoRepository.findById(request.pathVariable("id"));
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromPublisher(todo.flatMap(this::delete), ToDo.class));
  }

  private Mono<ServerResponse> findById(String id) {
    Mono<ToDo> todo = this.todoRepository.findById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    return todo
            .flatMap(t -> ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(fromValue(t))
                      )
            .switchIfEmpty(notFound);
  }

  private Mono<ToDo> save(ToDo todo) {
    return Mono.fromSupplier(() -> {
        todoRepository
          .save(todo)
          .subscribe();
        return todo;
      }
    );
  }

  private Mono<ToDo> delete(ToDo todo) {
    return Mono.fromSupplier(() -> {
        todoRepository
          .delete(todo)
          .subscribe();
        return todo;
      }
    );
  }

  private Mono<ToDo> update(ToDo todo) {
    ToDo updatedTodo = new ToDo();
    updatedTodo.setId(todo.getId());
    updatedTodo.setDescription(todo.getDescription());
    updatedTodo.setCompleted(todo.isCompleted());
    updatedTodo.setCreated(todo.getCreated());
    updatedTodo.setModified(Instant.now());
    return Mono.fromSupplier(() -> {
      todoRepository
        .save(updatedTodo)
        .subscribe();
      return updatedTodo;
    });
  }

}
