import { Component, OnInit } from '@angular/core';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  boardMessage?: string;
  errorMessage?: string;
  

  constructor(private boardsService: BoardsService) { 

  }

  ngOnInit(): void {
    this.boardsService.getUserBoard()
      .subscribe(data => this.boardMessage = data,
                error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

}
