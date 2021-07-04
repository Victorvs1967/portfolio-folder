import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PassDataService } from 'src/app/authentication/services/pass-data.service';
import { TokenStorageService } from 'src/app/authentication/services/token-storage.service';
import { ModalService } from 'src/app/services/modal.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  public display$: Subscription;
  public passDataSubscription: Subscription;
  authority?: string;
  isLoggedIn: boolean;
  username?: string;


  constructor(private router: Router, private tokenStorage: TokenStorageService, private passDataService: PassDataService, private modalService: ModalService) {
    this.display$ = this.modalService.watch().subscribe();
    this.passDataSubscription = this.passDataService.passDataEvent.subscribe((data: boolean) => this.isLoggedIn = data);
    this.isLoggedIn = false;
  }

  ngOnInit(): void {
    this.username = this.tokenStorage.getUsername() ?? 'Anonymous';
    this.authority = this.tokenStorage.getAutoritiesName();
  }

  ngOnDestroy() {
    this.display$.unsubscribe();
    this.passDataSubscription.unsubscribe();

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
