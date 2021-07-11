import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenStorageService } from '../authentication/services/token-storage.service';
import { Post } from '../models/post';
import { PostDto } from '../models/postDto';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class BoardsService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }

  getUserBoard(): Observable<string> {
    return this.http.get(environment.mainUrl + 'user', { responseType: 'text' });
  }

  getModBoard(): Observable<string> {
    return this.http.get(environment.mainUrl + 'mod', { responseType: 'text' });
  }
  
  getAllBoard(): Observable<string> {
    return this.http.get(environment.mainUrl + 'all', { responseType: 'text' });
  }

  getAdminBoard(): Observable<User[]> {
    return this.http.get<User[]>(environment.userUrl);
  }

  delete(username: string): Observable<User> {
    return this.http.delete<User>(environment.userUrl + username);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(environment.userUrl + user.username, user);
  }

  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(environment.postUrl + 'all');
  }

  getPost(id: string): Observable<Post> {
    return this.http.get<Post>(environment.postUrl + 'get/' + id);
  }

  createPost(post: PostDto): Observable<Post> {
    return this.http.post<Post>(environment.postUrl, post);
  }

  updatePost(post: PostDto): Observable<Post> {
    return this.http.put<Post>(environment.postUrl, post);
  }

  deletePost(id: string): Observable<Post> {
    return this.http.delete<Post>(environment.postUrl + id);
  }

}
