import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TokenStorageService } from 'src/app/authentication/services/token-storage.service';
import { PostDto } from 'src/app/models/postDto';
import { BoardsService } from 'src/app/services/boards.service';
import { ModalService, ModalState } from 'src/app/services/modal.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent implements OnInit {

  postForm: FormGroup;
  display: ModalState;
  errorMessage: string = '';
  authority: string;

  constructor(private modalService: ModalService, private tokenStorage: TokenStorageService, private boardsService: BoardsService, private formBuilder: FormBuilder, private router: Router) { 
    
    this.display = 'open';
    this.authority = this.tokenStorage.getAutoritiesName();

    this.postForm = this.formBuilder.group({
      title: [null, { validators: [Validators.required], updateOn: "change" }],
      content: [null, { validators: [Validators.nullValidator] }],
      username: [this.authority, { validators: [Validators.required], updateOn: "change" }]
    })

  }

  ngOnInit(): void {
    this.display = 'open';
  }

  onSubmit() {
    const post: PostDto = {
      title: this.postForm.controls.title.value,
      content: this.postForm.controls.content.value,
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
