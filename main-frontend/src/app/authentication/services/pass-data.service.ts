import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class PassDataService {

  public passDataEvent = new EventEmitter();

  constructor() { }
}
