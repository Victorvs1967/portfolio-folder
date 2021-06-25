import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent implements OnInit {

  user?: User;
  newUser?: User;
  editedForm?: FormGroup;
  isEdited: boolean;

  constructor(private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute, private boardsService: BoardsService) { 
    this.isEdited = false;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.boardsService.getAdminBoard().subscribe(data => {
        this.user = data.find(user => user.username === params.username);
        this.editedForm = this.formBuilder.group({
          fullname: [this.user?.fullname, { validators: [Validators.required], updateOn: "change" }],
          username: [this.user?.username, { validators: [Validators.required], updateOn: "change" }],
          email: [this.user?.email, { validators: [Validators.required], updateOn: "change" }],
          role: [this.user?.roles[0], { validators: [Validators.required], updateOn: "change" }]
        });
      });
    });
  }

  onSubmit() {
    this.newUser = new User(
      this.editedForm?.controls.fullname.value,
      this.editedForm?.controls.username.value,
      this.editedForm?.controls.email.value,
      this.user ? this.user.password : ''
    );
    let inRole = this.editedForm?.controls.role.value;
    if (this.user) {
      for (let role of this.user.roles) this.newUser.roles.push(role);
      if (inRole) this.newUser.roles.push(inRole);
      this.boardsService.update(this.newUser).subscribe(() => {
        this.isEdited = true;
        this.router.navigateByUrl('admin');
      })
    }

  }



}
