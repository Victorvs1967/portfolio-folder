import { Component, OnInit } from '@angular/core';
import { Todo } from 'src/app/model/todo.model';
import { TodoService } from 'src/app/service/todo.service';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.scss']
})
export class TodosComponent implements OnInit {

  dataSource: Todo[] = [];
  displayedColumns = ['description', 'created', 'modified', 'completed'];

  constructor(private todoService: TodoService) { }

  ngOnInit(): void {
    this.todoService.getAllTodos().subscribe(data => this.dataSource = data);
  }

  onChange(id: string) {
    const task = this.dataSource.filter(t => t.id === id)[0];
    task.completed = !task.completed;
    this.todoService.update(task).subscribe(t => console.log(t));
  }

}
