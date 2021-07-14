import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Editor, toHTML } from 'ngx-editor';
import { PostDto } from 'src/app/models/postDto';
import { BoardsService } from 'src/app/services/boards.service';

@Component({
  selector: 'app-update-post',
  templateUrl: './update-post.component.html',
  styleUrls: ['./update-post.component.scss']
})
export class UpdatePostComponent implements OnInit {

  editor: Editor;
  html?: '<p>Hello, World!</p>';
  form: FormGroup;
  postId: any;
  post_id?: string;
  author?: string;
  errorMessage: string = '';

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router, private boardsService: BoardsService) { 
    this.editor = new Editor();
    this.form = this.formBuilder.group({
      title: [null, { validators: [Validators.required], updateOn: "change" }],
      editorContent: [null, { validators: [Validators.nullValidator], updateOn: "change" }]
    })
  }

  ngOnInit(): void {
    this.route.params.subscribe(p => {
      this.post_id = p.id;
      this.boardsService.getPost(p.id)
      .subscribe(s => {
        this.postId = p.id;
        this.author = s.username;
        this.form.controls.title.setValue(s.title);
        this.editor.commands
        .insertHTML(toHTML(JSON.parse(s.content)))
        .focus()
        .scrollIntoView()
        .exec();
      })
    });
  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  onSubmit() {
    if (this.post_id && this.author) {
      const post: PostDto = {
        id: this.postId,
        _id: this.post_id,
        title: this.form.controls.title.value,
        content: JSON.stringify(this.form.controls.editorContent.value),
        username: this.author
      }
      this.boardsService.updatePost(post).subscribe(data => console.log(data), error => this.errorMessage = `${error.status}: ${error.message}`);
    }
    this.close();

  }

  close() {
    this.form.reset();
    this.router.navigate(['posts']);
  }

}
