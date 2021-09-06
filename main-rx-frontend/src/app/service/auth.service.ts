import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LoginRequestModel } from '../model/login-request.model';
import { TokenResponse } from '../model/token-response.model';
import { User } from '../model/user.model';
import { TokenStorageService } from './token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user?: User;

  constructor(private http: HttpClient, private router: Router, private tokenStorage: TokenStorageService) { }

  login(user: LoginRequestModel): Observable<TokenResponse> {
    this.tokenStorage.signOut();
    return this.http.post<TokenResponse>(environment.authUrl + '/login', user, httpOptions)
            .pipe(map((data: any) => {
              if (data.token != null) {
                this.tokenStorage.saveUsername(data.username);
                this.tokenStorage.saveToken(data.token);
                this.tokenStorage.saveAuthorities(data.role);
              }
              return data;
            }));
  }

  signup(user: LoginRequestModel): Observable<User> {
    return this.http.post<User>(environment.authUrl + '/signup', user, httpOptions);
  }

}
