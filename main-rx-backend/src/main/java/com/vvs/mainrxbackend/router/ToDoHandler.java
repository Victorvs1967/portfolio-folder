package com.vvs.mainrxbackend.router;

import com.vvs.mainrxbackend.config.JwtUtil;
import com.vvs.mainrxbackend.model.ToDo;
import com.vvs.mainrxbackend.model.User;
import com.vvs.mainrxbackend.repository.ToDoRepository;
import com.vvs.mainrxbackend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.*;

import java.net.http.HttpHeaders;

@Data
@Component
public class ToDoHandler {

  @Autowired
  private ToDoRepository todoRepository;

  @Autowired
  private UserRepository userRepository;
  
  @Autowired
  private JwtUtil jwtUtil;

  private String id;

  public Mono<ServerResponse> getToDo(ServerRequest request) {
    return findById(request.pathVariable("id"));
  }

  public Mono<ServerResponse> getToDos(ServerRequest request) {
    String username = jwtUtil.extractUsername(request.headers().firstHeader("authorization").substring(7));
    Mono<User> user = userRepository.findByUsername(username);
    user.subscribe();
    user.map(u -> u.getId().toHexString()).subscribe(this::setId);
    Flux<ToDo> toDos = this.getToDosByUserId(this.getId());
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
    String id = request.pathVariable("id");
    Mono<ToDo> reqTodo = request.bodyToMono(ToDo.class);
    Mono<ToDo> dbTodo = todoRepository.findById(id);
    Mono<ToDo> todo = dbTodo.flatMap(td -> reqTodo.doOnSuccess(t -> t.setId(td.getId())));
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(todo.flatMap(this::save), ToDo.class);
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
        if (todo.getId() == null) todo.setUserId(this.getId());
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

  private Flux<ToDo> getToDosByUserId(String id) {
    return todoRepository.findAll().filter(t -> t.getUserId() != null ? t.getUserId().equals(id) : false);
  }

}
