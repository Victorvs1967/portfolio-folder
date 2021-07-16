package com.vvs.mainrxbackend.router;

import com.vvs.mainrxbackend.model.ToDo;
import com.vvs.mainrxbackend.repository.ToDoRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.java.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;

@Log
@Component
public class ToDoHandler {

  @Autowired
  private ToDoRepository todoRepository;
  
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
            .log()
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromPublisher(
                  todo
                  .log().doOnNext(t -> this.todoRepository.findById(t.getId()))
                  .flatMap(this::save), ToDo.class));
  }

  public Mono<ServerResponse> deleteTodo(ServerRequest request) {
    ObjectId id = new ObjectId(request.pathVariable("id"));
    Mono<ToDo> todo = this.todoRepository.findById(id);
    return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(fromPublisher(todo.flatMap(this::delete), ToDo.class));
  }

  private Mono<ServerResponse> findById(String id) {
    Mono<ToDo> todo = this.todoRepository.findById(new ObjectId(id));
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    return todo.flatMap(t -> ServerResponse
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
    });
  }
}
