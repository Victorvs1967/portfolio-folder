import { Component, OnInit } from '@angular/core';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {


  boardMessage?: string;
  errorMessage?: string;

  constructor(private boardsService: BoardsService) { }

  ngOnInit(): void {
    this.boardsService.getAdminBowrd()
      .subscribe(data => this.boardMessage = data,
        error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

}
