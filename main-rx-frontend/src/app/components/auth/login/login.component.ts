import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { ModalService, ModalState } from 'src/app/service/modal.service';
import { TokenStorageService } from 'src/app/service/token-storage.service';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  isLoggedIn: boolean;
  isLoggedFalse: boolean;
  errorMessage: string;
  roles: string[];
  public display: ModalState;

  constructor( private formBuilder:FormBuilder, 
              private router: Router, 
              private modalService: ModalService, 
              private authService: AuthService,
              private tokenStorage: TokenStorageService) {

    this.isLoggedIn = false;
    this.isLoggedFalse = false;
    this.errorMessage = '';
    this.roles = [];
    this.display = 'open';

  }

  loginForm: FormGroup = this.formBuilder.group({
    username: [null, { validators: [Validators.required], updateOn: "change"}],
    password: [null, { validators: [Validators.required], updateOn: "change"}],
  });

  ngOnInit(): void {
    this.display = 'open';
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
      this.router.navigate(['todo']);
    }
  }

  clickLogin() {
    const username = this.loginForm.controls.username.value;
    const password = this.loginForm.controls.password.value;
    this.authService.login(username, password).subscribe(() => {
      this.isLoggedFalse = false;
      this.isLoggedIn = true;
      this.router.navigate(['todo']);
    }, error => this.isLoggedFalse = true);
  }

  clickRegister() {
    this.router.navigate(['signup'])
  }

  reloadPage() {
    this.router.navigate(['todo'])
    window.location.reload();
  }

  close() {
    this.display = 'close';
    this.loginForm.reset();
    this.modalService.close();
    this.router.navigate(['todo']);
  }

}
