import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Todo } from '../model/todo.model';
import { TodoDto } from '../model/todoDto.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http: HttpClient) { }

  getAllTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(environment.todoUrl);
  }
  
  save(todo: TodoDto): Observable<TodoDto> {
    return this.http.post<TodoDto>(environment.todoUrl, todo);
  }

  update(todo: TodoDto): Observable<TodoDto> {
    return this.http.put<TodoDto>(environment.todoUrl, todo);
  }

  delete(id: string): Observable<Todo> {
    return this.http.delete<Todo>(environment.todoUrl + '/' + id);
  }

}
