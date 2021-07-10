import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Editor } from 'ngx-editor';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.scss']
})
export class UpdatePostComponent implements OnInit {

  editor: Editor;
  html?: '<p>Hello, World!</p>';

  constructor(private route: ActivatedRoute, private boardsService: BoardsService) { 
    this.editor = new Editor();
  }

  ngOnInit(): void {
    this.route.params.subscribe(p => this.boardsService.getPost(p.id).subscribe(s => console.log(s)));
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

}
