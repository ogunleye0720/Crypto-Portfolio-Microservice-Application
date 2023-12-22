import { Injectable } from '@angular/core';
import { TreeMapData } from '@swimlane/ngx-charts';
import { BehaviorSubject } from 'rxjs';
import { UserWallet } from 'src/app/balance/model/user-wallet';
import { TokenChartTreeData } from '../model/TokenChartTreeData';

@Injectable({
  providedIn: 'root'
})
export class TokenTreeChartDataService {

  private tokenChartTreeData: TokenChartTreeData[] = [];
  dataSubject = new BehaviorSubject<TokenChartTreeData[]>([]);
  data$ = this.dataSubject.asObservable();

  constructor() { }

  setData(userWallet:UserWallet) {
    const data: TokenChartTreeData = { name: userWallet.symbol, value: userWallet.total.totalBalance };    
    
    this.tokenChartTreeData.push(data);
    if (this.tokenChartTreeData.length === 3) {
      this.dataSubject.next(this.tokenChartTreeData);
      this.tokenChartTreeData = [];
    }    
  }
}
