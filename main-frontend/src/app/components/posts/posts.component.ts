import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  dataSource: Post[] = [];
  displayedColumns = ['title', 'content', 'username', 'createOn', 'updateOn'];
  errorMessage = '';

  constructor(private boardsService: BoardsService) { }

  ngOnInit(): void {
    this.boardsService.getAllPosts()
      .subscribe(data => this.dataSource = data, error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`)
  }

}
