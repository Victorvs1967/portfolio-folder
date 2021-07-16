import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Todo } from '../model/todo.model';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http: HttpClient) { }

  getAllTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(environment.todoUrl);
  }
  
  save(id: string): Observable<Todo> {
    return this.http.post<Todo>(environment.todoUrl, id);
  }

  update(todo: Todo): Observable<Todo> {
    return this.http.put<Todo>(environment.todoUrl, todo);
  }
}
