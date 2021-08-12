import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { ModalService, ModalState } from 'src/app/service/modal.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public display: ModalState;

  constructor(private formBuilder: FormBuilder, private router: Router, private modalService: ModalService, private authService: AuthService) {
    this.display = 'open';
  }

  signupForm: FormGroup = this.formBuilder.group({
    username: [null, { validators: [Validators.required], updateOn: "change" }],
    password: [null, { validators: [Validators.required], updateOn: "change" }],
  });

  ngOnInit(): void {
    this.display = 'open';
  }

  clickRegister() {
    const username = this.signupForm.controls.username.value;
    const password = this.signupForm.controls.password.value;
    this.authService.signup(username, password).subscribe(data => console.log(data));
    this.router.navigate(['login']);
  }

  reloadPage() {
    window.location.reload();
  }

  close() {
    this.display = 'close';
    this.signupForm.reset();
    this.modalService.close();
    this.router.navigate(['todo']);
  }

}
