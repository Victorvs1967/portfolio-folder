import { Component, OnInit } from '@angular/core';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  boardMessage?: string;
  errorMessage?: string;

  constructor(private boardService: BoardsService) { }

  ngOnInit(): void {
    this.boardService.getAllBoard()
      .subscribe(data => this.boardMessage = data,
        error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

}
