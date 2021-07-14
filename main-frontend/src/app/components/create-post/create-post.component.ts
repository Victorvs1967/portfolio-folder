import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Editor, toHTML } from 'ngx-editor';
import { TokenStorageService } from 'src/app/authentication/services/token-storage.service';
import { PostDto } from 'src/app/models/postDto';
import { User } from 'src/app/models/user';
import { BoardsService } from 'src/app/services/boards.service';
import { ModalService, ModalState } from 'src/app/services/modal.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  editor: Editor;
  postForm: FormGroup;
  display: ModalState;
  errorMessage: string = '';
  authority: string;
  users: User[] = [];

  constructor(private modalService: ModalService, private tokenStorage: TokenStorageService, private boardsService: BoardsService, private formBuilder: FormBuilder, private router: Router) { 
    
    this.display = 'open';
    this.authority = this.tokenStorage.getAutoritiesName();
    this.boardsService.getAdminBoard().subscribe(data => data.forEach(user => this.users.push(user)));

    this.editor = new Editor();

    this.postForm = this.formBuilder.group({
      title: [null, { validators: [Validators.required] }],
      content: [null, { validators: [Validators.nullValidator] }],
      username: [this.authority, { validators: [Validators.required] }]
    })

  }

  ngOnInit(): void {
    this.display = 'open';

    this.editor.commands
      .focus()
      .scrollIntoView()
      .exec();

  }

  ngOnDestroy() {
    this.editor.destroy();
  }

  onSubmit() {
    const post: PostDto = {
      title: this.postForm.controls.title.value,
      content: JSON.stringify(this.postForm.controls.content.value),
      username: this.postForm.controls.username.value
    }
    this.boardsService.createPost(post).subscribe(data => console.log(data), error => this.errorMessage = `${error.status}: ${JSON.parse(error.error).message}`);
    this.router.navigate(['home']);
    this.close();
  }

  close() {
    this.display = 'close';
    this.postForm.reset();
    this.modalService.close();
  }

}
