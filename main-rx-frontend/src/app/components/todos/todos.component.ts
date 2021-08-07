import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Todo } from 'src/app/model/todo.model';
import { TodoService } from 'src/app/service/todo.service';
import Swal from 'sweetalert2';
import { DialogBoxComponent } from '../dialog-box/dialog-box.component';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.scss']
})
export class TodosComponent implements OnInit {

  dataSource: Todo[] = [];
  displayedColumns = ['completed', 'description', 'created', 'modified', 'buttons'];

  description: string = '';

  constructor(private todoService: TodoService, public dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.todoService.getAllTodos().subscribe(data => {
      data.forEach(todo => {
        this.dataSource = [todo, ...this.dataSource];        
      });
    });
  }

  onChange(id: string) {
    const todo = this.dataSource.filter(t => t.id === id);
    todo[0].completed = !todo[0].completed;
    this.todoService.update(todo[0]).subscribe(t => Swal.fire(t.completed ? 'Todo completed.' : 'Todo uncompleted'));
  }

  delete(id: string) {
    this.todoService.delete(id).subscribe(t => this.warning('Todo deleted!'));
  }

  add() {
    const todo = new Todo();
    let dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '50%',
      panelClass: 'dialog-box',
      data: { description: this.description }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.description = result;
      console.log(result);
      if (this.description != undefined) {
        if (this.description == '') {
          Swal.fire({
            text: 'Description cannot be empty..!',
            background: '#b0b0b0'
          }).then(() => this.add());
        } else {
          todo.description = this.description;
          this.todoService.save(todo).subscribe(() => window.location.reload());
        }
      }
    });
  }

  edit(todo: Todo) {
    let dialogRef = this.dialog.open(DialogBoxComponent, {
      width: '50%',
      panelClass: 'dialog-box',
      data: { description: todo.description }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.description = result;
      if (this.description != undefined) {
        if (this.description == '') {
          Swal.fire({
            text: 'Description cannot be empty..!',
            background: '#f0f0f0'
          }).then(() => this.edit(todo));
        } else {
          todo.description = this.description;
          this.todoService.update(todo).subscribe(() => window.location.reload());
        }
      }
    });
  }

  warning(message: string) {
    Swal.fire(message).then(() => window.location.reload());
  }

  reload() {
    this.router.navigate(['']);
    window.location.reload();

  }

}
