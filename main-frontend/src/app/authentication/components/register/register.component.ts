import { NullVisitor } from '@angular/compiler/src/render3/r3_ast';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupInfo } from '../../models/signup-info';
import { AuthenticationService } from '../../services/authentication.service';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  signUpInfo?: SignupInfo;
  isSignedUp: boolean;
  isSignedUpFailed: boolean;
  errorMessage: string;
  authority?: string;

  registerForm: FormGroup = this.formBuilder.group({
    name: [null, { validators: [Validators.nullValidator], updateOn: "change" }],
    username: [null, { validators: [Validators.required], updateOn: "change" }],
    email: [null, { validators: [Validators.required, Validators.email], updateOn: "change" }],
    password: [null, { validators: [Validators.required], updateOn: "change" }],
    role: ['user', { validators: [Validators.required], updateOn: "change" }]
  })

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private tokenStorage: TokenStorageService,
    private router: Router) { 
    this.isSignedUp = false;
    this.isSignedUpFailed = false;
    this.errorMessage = '';
  }

  ngOnInit(): void {
    this.authority = this.tokenStorage.getAutoritiesName();
  }

  onSubmit() {
    this.signUpInfo = new SignupInfo(
      this.registerForm.controls.name.value,
      this.registerForm.controls.username.value,
      this.registerForm.controls.email.value,
      this.registerForm.controls.password.value
    )

    let inRole = this.registerForm.controls.role.value;
    if (inRole) this.signUpInfo.roles.push(inRole);

    this.authenticationService.signUp(this.signUpInfo).subscribe(data => {
      this.isSignedUp = true;
      this.isSignedUpFailed = false;
      this.router.navigate(['login']);
    }, error => {
      this.errorMessage = error.error.message;
      this.isSignedUpFailed = true;
    });
  }

  cancel() {
    this.router.navigate(['']);
  }

}
