import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/models/role';
import { User } from 'src/app/models/user';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-users-table',
  templateUrl: './users-table.component.html',
  styleUrls: ['./users-table.component.scss']
})
export class UsersTableComponent implements OnInit {

  @Input()
  dataSource: User[] = [];
  displayedColumns = ['fullname', 'username', 'email', 'roles', 'buttons'];
  roles: Role[] = [
    { name: 'admin', viewName: 'ROLE_ADMIN'}, 
    { name: 'mod', viewName: 'ROLE_MODERATOR'}, 
    { name: 'user', viewName: 'ROLE_USER'}
  ];

  errorMessage?: string;

  selectedRoles = new FormControl();
  newUser?: User;

  constructor(private boardsService: BoardsService, private router: Router) { 
  }

  ngOnInit(): void {
  }

  delete(user: string) {
    this.boardsService.delete(user).subscribe(() => this.reloadPage(), error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

  save(user: User) {
    
    this.newUser = new User(
      user.fullname,
      user.username,
      user.email,
      user.password
    );

    let newRoles = this.selectedRoles.value ? this.selectedRoles.value : ['user'];

    if (!newRoles) newRoles = user.roles;
    for (let role of newRoles) this.newUser.roles.push(role);
    this.boardsService.update(this.newUser).subscribe(() => this.reloadPage(), error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

  reloadPage() {
    window.location.reload();
  }

}
