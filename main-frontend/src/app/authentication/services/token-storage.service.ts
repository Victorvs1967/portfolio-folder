import { Injectable } from '@angular/core';

const TOKEN_KEY = 'AuthToken';
const USERNAME_KEY = 'AuthUsername';
const AUTHORITIES_KEY = 'AuthAuthorities';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  private roles: Array<string> = [];

  constructor() { }

  signOut() {
    window.sessionStorage.clear();
  }

  saveToken(token: string): any {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  getToken(): string | null {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  saveUsername(username: string) {
    window.sessionStorage.removeItem(USERNAME_KEY);
    window.sessionStorage.setItem(USERNAME_KEY, username);
  }

  getUsername(): string | null {
    return window.sessionStorage.getItem(USERNAME_KEY);
  }

  saveAuthorities(authorities: string[]) {
    window.sessionStorage.removeItem(AUTHORITIES_KEY);
    window.sessionStorage.setItem(AUTHORITIES_KEY, JSON.stringify(authorities));
  }

  getAuthorities() {
    this.roles = [];
    if (window.sessionStorage.getItem(AUTHORITIES_KEY)) {
      JSON.parse(window.sessionStorage.getItem(AUTHORITIES_KEY) ?? '')
        .forEach((role: any) => this.roles.push(role));
    }
    return this.roles;
  }

  getAutoritiesName(): string {
    let authority: string = '';
    if (this.getToken()) {
      this.getAuthorities().every(role => {
        if (role === 'ROLE_ADMIN') {
          authority = 'admin';
          return false;
        } else if (role === 'ROLE_MODERATOR') {
          authority = 'mod';
          return false;
        }
        authority = 'user';
        return true;
      });
    }
    return authority;
  }


}
