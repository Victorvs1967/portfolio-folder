package com.vvs.mainrxbackend.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.vvs.mainrxbackend.config.ToDoHandler;

@Configuration
public class ToDoRouter {
  
  @Bean
  public RouterFunction<ServerResponse> monoRouterFunction(ToDoHandler todoHandler) {
    
    return route(GET("/todo/{id}").and(accept(APPLICATION_JSON)), todoHandler::getToDo)
            .andRoute(GET("/todo").and(accept(APPLICATION_JSON)), todoHandler::getToDos)
            .andRoute(POST("/todo").and(accept(APPLICATION_JSON)), todoHandler::newToDo)
            .andRoute(DELETE("/todo/{id}").and(accept(APPLICATION_JSON)), todoHandler::deleteTodo);
  }
}
