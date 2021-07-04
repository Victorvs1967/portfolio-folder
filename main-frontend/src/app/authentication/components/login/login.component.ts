import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalService, ModalState } from 'src/app/services/modal.service';
import { LoginInfo } from '../../models/login-info';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  isLoggedIn: boolean;
  isLogingFailed: boolean;
  errorMessage: string;
  roles: string[];
  private loginInfo?: LoginInfo;
  public display: ModalState;


  constructor(private formBuilder: FormBuilder, 
              private router: Router, 
              private authenticationService: AuthenticationService,
              private tokenStorage: TokenStorageService,
              private modalService: ModalService) { 

    this.isLoggedIn = false;
    this.isLogingFailed = false;
    this.errorMessage = '';
    this.roles = [];
    this.display = 'open';
  }

  loginForm: FormGroup = this.formBuilder.group({
    username: [null, { validators: [Validators.required], updateOn: "change" }],
    password: [null, { validators: [Validators.required], updateOn: "change" }]
  });

  ngOnInit(): void {
    this.display = 'open';
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
    }
  }

  clickLogin() {
    this.loginInfo = new LoginInfo(
      this.loginForm.controls.username.value,
      this.loginForm.controls.password.value
    );
    this.authenticationService.attempAuth(this.loginInfo)
      .subscribe(() => {
      this.isLoggedIn = true;
      this.isLogingFailed = false;
      this.roles = this.tokenStorage.getAuthorities();
      this.reloadPage();
    }, error => {
      this.errorMessage = error.error.message;
      this.isLogingFailed = true;
    });
    this.close();
    this.router.navigate(['home']);
  }

  clickRegister() {
    this.close();
    this.router.navigate(['register']);
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
