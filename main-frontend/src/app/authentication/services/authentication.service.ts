import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators'
import { AuthConfig } from '../models/config';
import { JwtResponse } from '../models/jwt-response';
import { LoginInfo } from '../models/login-info';
import { SignupInfo } from '../models/signup-info';
import { TokenStorageService } from './token-storage.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService, @Inject('config') private config: AuthConfig) { }

  attempAuth(credentials: LoginInfo): Observable<JwtResponse>{
    return this.http.post<JwtResponse>(this.config.authEndpoint + '/signin', credentials, httpOptions)
      .pipe(map(res => {
        this.tokenStorage.saveUsername(res.username);
        this.tokenStorage.saveAuthorities(res.roles);
        this.tokenStorage.saveToken(res.token);
        return res;
      }), shareReplay());
  }

  signUp(info: SignupInfo): Observable<string> {
    console.log(info);
    return this.http.post<string>(this.config.authEndpoint + '/signup', info, httpOptions);
  }

}
