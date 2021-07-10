import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/models/post';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  dataSource: Post[] = [];
  displayedColumns = ['_id', 'title', 'content', 'username', 'createOn', 'updateOn', 'button'];
  errorMessage = '';

  constructor(private boardsService: BoardsService, private router: Router) { }

  ngOnInit(): void {
    this.boardsService.getAllPosts()
      .subscribe(data => this.dataSource = data, error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
  }

  edit(id: string) {
    this.router.navigate(['post', id]);
  }

}
