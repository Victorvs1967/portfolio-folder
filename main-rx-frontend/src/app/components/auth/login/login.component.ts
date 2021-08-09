import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalService, ModalState } from 'src/app/service/modal.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public display: ModalState;

  constructor(private formBuilder:FormBuilder, private router: Router, private modalService: ModalService) { 
    this.display = 'open';
  }

  loginForm: FormGroup = this.formBuilder.group({
    username: [null, { validators: [Validators.required], updateOn: "change"}],
    password: [null, { validators: [Validators.required], updateOn: "change"}],
  });

  ngOnInit(): void {
    this.display = 'open';
  }

  clickLogin() {

  }

  clickRegister() {

  }

  reloadPage() {
    window.location.reload();
  }

  close() {
    this.display = 'close';
    this.loginForm.reset();
    this.modalService.close();
    this.router.navigate(['']);
  }

}
