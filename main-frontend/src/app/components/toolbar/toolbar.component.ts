import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PassDataService } from 'src/app/authentication/services/pass-data.service';
import { TokenStorageService } from 'src/app/authentication/services/token-storage.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  authority?: string;
  isLoggedIn: boolean;
  username?: string;

  constructor(private router: Router, private tokenStorage: TokenStorageService, private passDataService: PassDataService) {
    this.passDataService.passDataEvent.subscribe((data: boolean) => this.isLoggedIn = data);
    this.isLoggedIn = false;
  }

  ngOnInit(): void {
    this.username = this.tokenStorage.getUsername() ?? 'Anonymous';
    this.authority = this.tokenStorage.getAutoritiesName();
  }

  login() {
    this.router.navigate(['login']);
  }

  exit() {
    this.isLoggedIn = false;
    this.authority = undefined;
    this.router.navigate(['logout']);
  }

  register() {
    this.router.navigate(['register']);
  }

}
