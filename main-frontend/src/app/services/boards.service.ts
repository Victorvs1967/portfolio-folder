import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenStorageService } from '../authentication/services/token-storage.service';
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
    return this.http.get<User[]>(environment.mainUrl + 'users');
  }

  delete(username: string): Observable<User> {
    return this.http.delete<User>(environment.mainUrl + username);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(environment.mainUrl + user.username, user);
  }

}
