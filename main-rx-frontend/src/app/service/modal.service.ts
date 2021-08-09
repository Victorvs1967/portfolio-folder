import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export type ModalState = 'open' | 'close';

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  private display: BehaviorSubject<ModalState> = new BehaviorSubject<ModalState>('close');

  watch(): Observable<ModalState> {
    return this.display;
  }

  open() {
    this.display.next('open');
  }

  close() {
    this.display.next('close');
  }
}
