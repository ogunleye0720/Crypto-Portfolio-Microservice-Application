import { Component } from '@angular/core';
import { map, Observable } from 'rxjs';
import { TotalValueService } from 'src/app/value/service/total-value.service';
import { UserBalance } from '../../model/user-balance';
import { BalanceService } from '../../service/balance.service';

@Component({
  selector: 'app-abstract-balance',
  templateUrl: './abstract-balance.component.html',
  styleUrls: ['./abstract-balance.component.scss']
})
export class AbstractBalanceComponent {
  public data$ = this.balanceService.getDataSource();
  public dataSource$: Observable<UserBalance[]>;
  public rows: UserBalance[];
  amountData: any;
  showDetails = 0;

  url: string = this.balanceService.getEthereumWalletBalancesURL;

  constructor(public balanceService: BalanceService, private totalValueService: TotalValueService) { }

  ngOnInit(): void {
    this.dataSource$ = this.balanceService.getWalletsBalance(this.url).pipe(
      map(data => data.balance),
    );

    this.data$.subscribe(data => {
      this.amountData = [data.symbol, data.total.totalQuantity, data.total.totalBalance];
    });

    this.balanceService.getDataSource().subscribe(data => {
      this.rows = data.balance;
    });

    this.toggleView();
  }

  toggleView() {
    if (this.showDetails === 3) {
      this.showDetails = 0;
    }

    this.showDetails += 1;
  }

  toggleViewMatic() {
    if (this.showDetails === 3) {
      this.showDetails = 0;
    }

    this.showDetails += 1;
  }

  getColor(currentBalance: number, previousBalance: number) {
    return previousBalance < currentBalance ? '#FF4D00' : '#07F36B';
  }

  getNumberWallets(): number {
    return this.rows.length;
  }

  setTokenName(): string {
    return "token_name"
  }
}