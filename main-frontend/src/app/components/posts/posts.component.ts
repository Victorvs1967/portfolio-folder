import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { toHTML } from 'ngx-editor';
import { Post } from 'src/app/models/post';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  dataSource: Post[] = [];
  displayedColumns = ['title', 'content', 'username', 'createOn', 'updateOn', 'buttons'];
  errorMessage = '';

  constructor(private boardsService: BoardsService, private router: Router) { }

  ngOnInit(): void {
    this.boardsService.getAllPosts()
      .subscribe(data => {
        data.forEach(post => {
          post.content = JSON.parse(post.content).content[0].content[0].text;
          this.dataSource = [post, ...this.dataSource];
        });

    }, error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
    console.log(this.dataSource);
  }

  edit(id: string) {
    this.router.navigate(['post', id]);
  }

  delete(id: string) {
    this.boardsService.deletePost(id).subscribe(data => console.log(data), error => this.errorMessage = `${error.status}: ${error.message}`);
    this.reloadPage();
  }

  view(id: string) {
    this.router.navigate(['view', id]);
  }

  reloadPage() {
    window.location.reload();
  }



}
