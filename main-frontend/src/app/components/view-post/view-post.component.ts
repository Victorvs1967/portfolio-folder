import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { toDoc, toHTML } from 'ngx-editor';
import { Post } from 'src/app/models/post';
import { BoardsService } from 'src/app/services/boards.service';
import { ModalService, ModalState } from 'src/app/services/modal.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.scss']
})
export class ViewPostComponent implements OnInit {

  post?: Post;
  errorMessage?: string;
  display: ModalState;

  constructor(private boardsService: BoardsService, private modalService: ModalService, private router: Router, private route: ActivatedRoute) { 
    this.display = 'open';
  }

  ngOnInit(): void {
    this.route.params.subscribe(p => this.boardsService.getPost(p.id)
      .subscribe(post => {
        this.post = post;
        this.post.content = toHTML(JSON.parse(post.content));
      }, error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`));
  }

  close() {
    this.display = 'close';
    this.modalService.close();
    this.router.navigate(['posts']);
  }

}
