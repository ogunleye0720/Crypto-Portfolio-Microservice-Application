import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TokenCrypto } from '../model/token-crypto';

const apiGatewayURL = environment.apiGatewayURL;

@Injectable({
  providedIn: 'root'
})
export class PriceService {

  constructor(private http: HttpClient) { }

  private getPriceURL = apiGatewayURL + '/api/v1/cryptocurrency';

  getPrice(): Observable<TokenCrypto[]> {
    const data = this.http.get<TokenCrypto[]>(this.getPriceURL).pipe(
      catchError(this.handleError<TokenCrypto[]>('getPrice', []))
    );
    return data;
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return error;
    };
  }
}

