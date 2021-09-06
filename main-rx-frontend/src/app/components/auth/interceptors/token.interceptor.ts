import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenStorageService } from 'src/app/service/token-storage.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private tokenStorage: TokenStorageService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.tokenStorage.getToken();
    const username = this.tokenStorage.getUsername();
    if (username != null && token != null) {
      request = request.clone({ 
        setHeaders: {
          'Authorization': 'Bearer ' + token,
          'Access-Control-Allow-Origin': '*'
        }
      });
    } else {
      request = request.clone({
        setHeaders: {
          'Access-Control-Allow-Origin': '*'
        }
      });

    }
    return next.handle(request);
  }

}

export const AUTH_INTERCEPTOR_PROVIDER = [
  { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  // { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
];

