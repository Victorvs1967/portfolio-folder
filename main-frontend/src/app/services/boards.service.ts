import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TokenStorageService } from '../authentication/services/token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class BoardsService {

  private mainUrl = 'http://localhost:8080/api/main';

  constructor(private http: HttpClient, private tokenStorage: TokenStorageService) { }

  getUserBoard(): Observable<string> {
    return this.http.get(this.mainUrl + '/user', { responseType: 'text' });
  }

  getAdminBowrd(): Observable<string> {
    return this.http.get(this.mainUrl + '/admin', { responseType: 'text' });
  }

  getModBoard(): Observable<string> {
    return this.http.get(this.mainUrl + '/mod', { responseType: 'text' });
  }

  getAllBoard(): Observable<string> {
    return this.http.get(this.mainUrl + '/all', { responseType: 'text' });
  }

}
