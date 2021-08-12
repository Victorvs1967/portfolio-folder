import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { User } from '../model/user.model';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router, private tokenStorage: TokenStorageService) { }

  login(username: string, password: string): Observable<User> {
    this.tokenStorage.signOut();
    return this.http.post<User>(environment.authUrl + '/login', { username, password })
            .pipe(map((data: any) => {
              if (data.body != null) {
                let token = 'Bearer ' + data.body;
                this.tokenStorage.saveUsername(username);
                this.tokenStorage.saveToken(token);
              }
              return data;
            }));
  }

  signup(username: string, password: string): Observable<User> {
    return this.http.post<User>(environment.authUrl + '/signup', { username, password });
  }

}
