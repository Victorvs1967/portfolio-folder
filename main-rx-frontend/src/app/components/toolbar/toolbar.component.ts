import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  constructor(private tokenStorage: TokenStorageService, private router: Router) { 
  }

  ngOnInit(): void {
  }

  isAutorities(): boolean {
    return this.tokenStorage.isAutorities();
  }

}
