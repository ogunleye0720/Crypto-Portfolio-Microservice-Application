import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap } from 'rxjs';
import { InputWalletsDataService } from 'src/app/shared/services/input-wallets-data.service';
import { TotalValueService } from 'src/app/value/service/total-value.service';
import { environment } from 'src/environments/environment';
import { UserWallet } from '../model/user-wallet';
import { ErrorHandlerService } from './error-handler.service';
import { TokenTreeChartDataService } from 'src/app/shared/services/token-tree-chart-data.service';

const apiGatewayURL = environment.apiGatewayURL;

@Injectable({
  providedIn: 'root'
})
export class BalanceService {

  private dataSubject = new BehaviorSubject<UserWallet>({
    name: '',
    symbol: '',
    total: { totalQuantity: 0, totalBalance: 0, totalBalance1h: 0, totalBalance24h: 0, totalBalance7d: 0 },
    balance: []
  });
  private data$ = this.dataSubject.asObservable();

  public getEthereumWalletBalancesURL = apiGatewayURL + '/api/v1/wallet/eth/balance?wallets=';
  public getPolygonWalletBalancesURL = apiGatewayURL + '/api/v1/wallet/matic/balance?wallets=';
  public getAvalancheWalletBalancesURL = apiGatewayURL + '/api/v1/wallet/avax/balance?wallets=';

  constructor(
    private http: HttpClient,
    private errorHandler: ErrorHandlerService,
    private inputWalletsData: InputWalletsDataService,
    private totalValueService: TotalValueService,
    private tokenTreeChartDataService: TokenTreeChartDataService
  ) { }

  getWalletsBalance(url: string): Observable<UserWallet> {
    const wallets = this.inputWalletsData.getDataFromSessionStorage();
    const data = this.http.get<UserWallet>(url + wallets).pipe(
      tap(data => {
        data.balance.forEach(wallet => {
          wallet.walletAddress = this.trimAddress(wallet.walletAddress);
        });
        this.tokenTreeChartDataService.setData(data);
        this.totalValueService.setTotalValue(data.total);
        this.dataSubject.next(data);
      }),
      catchError((error: HttpErrorResponse) => this.errorHandler.handleHttpError(error))
    );
    return data;
  }

  trimAddress(address: string): string {
    return address.slice(0, 10);
  }

  getDataSource(): Observable<UserWallet> {
    return this.data$;
  }

}
