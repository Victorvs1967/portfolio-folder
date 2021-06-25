import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private tokenStorage: TokenStorageService, private router: Router) { }

  ngOnInit(): void {
    this.tokenStorage.signOut();
    this.router.navigate(['login']);
  }

}
