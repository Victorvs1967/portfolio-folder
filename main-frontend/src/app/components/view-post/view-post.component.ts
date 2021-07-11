import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from 'src/app/models/post';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.scss']
})
export class ViewPostComponent implements OnInit {

  post?: Post;
  errorMessage?: string;

  constructor(private boardsService: BoardsService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe(p => this.boardsService.getPost(p.id)
      .subscribe(post => this.post = post, error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`));
  }

}
