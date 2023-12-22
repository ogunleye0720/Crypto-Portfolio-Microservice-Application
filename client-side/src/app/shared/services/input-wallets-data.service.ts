import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InputWalletsDataService {

  private walletInputData = 'input-wallets-data';

  constructor() { }

  storeDataInSessionStorage(data: String): void {
    sessionStorage.setItem(this.walletInputData, JSON.stringify(data));
  }

  getDataFromSessionStorage(): string {
    const data = sessionStorage.getItem(this.walletInputData) || '';
    if (data !== null && data !== 'undefined' && data !== '') {
      return JSON.parse(data);
    } else {
      return '';
    }
  }
}
