import { Component, OnInit } from '@angular/core';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-mod',
  templateUrl: './mod.component.html',
  styleUrls: ['./mod.component.scss']
})
export class ModComponent implements OnInit {


  boardMessage?: string;
  errorMessage?: string;

  constructor(private boardService: BoardsService) { }

  ngOnInit(): void {
    this.boardService.getModBoard()
      .subscribe(data => this.boardMessage = data,
        error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

}
