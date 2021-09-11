import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Todo } from '../model/todo.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': '*'
  })
};

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private http: HttpClient) { }

  getAllTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(environment.todoUrl, httpOptions);
  }
  
  save(todo: Todo): Observable<Todo> {
    return this.http.post<Todo>(environment.todoUrl, todo, httpOptions);
  }

  update(todo: Todo): Observable<Todo> {
    return this.http.put<Todo>(environment.todoUrl + '/' + todo.id, todo, httpOptions);
  }

  delete(id: string): Observable<Todo> {
    return this.http.delete<Todo>(environment.todoUrl + '/' + id, httpOptions);
  }

}
