import { Component, OnInit, ViewChild } from '@angular/core';
import { TokenCrypto } from '../../model/token-crypto';
import { PriceService } from '../../service/price.service';
import { Observable } from 'rxjs';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { SatPopover } from '@ncstate/sat-popover';

@Component({
  selector: 'app-price-bar',
  templateUrl: './price-bar.component.html',
  styleUrls: ['./price-bar.component.scss'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        transform: 'translateX(0%)'
      })),
      state('out', style({
        transform: 'translateX(100%)'
      })),
      transition('in => out', animate('2500ms ease-in-out')),
      transition('out => in', animate('2500ms ease-in-out'))
    ])
  ]
})
export class PriceBarComponent implements OnInit {
  @ViewChild('priceBarPopover') priceBarPopover: SatPopover;
  instantAnchor: HTMLElement;

  private emptyTokenCryptoData: TokenCrypto = {name: '', symbol: '', price: {priceCurrent: 0, percentChange1h: 0, percentChange24h: 0, percentChange7d: 0, percentChange30d: 0, percentChange60d: 0, percentChange90d: 0}}; 
  
  prices: TokenCrypto[] = [];
  data: TokenCrypto;

  state = 'in';
  private currentDataIndex = 1;
  private barCliked = false;
  private closeTimer: any;

  constructor(private priceService: PriceService) {
    this.data = this.emptyTokenCryptoData;
  }

  ngOnInit(): void {
    this.priceService.getPrice().subscribe(requestData => {
      this.prices = requestData;
      if (this.prices.length > 0) {
        this.data = this.styleData(this.prices[0]);
      }
    });

    this.tokenData.subscribe(value => {
      this.data = value;
    });
  }

  tokenData = new Observable<TokenCrypto>(subscriber => {
    this.currentDataIndex = 1;
    setInterval(() => {
      if (this.state === 'out') {
        subscriber.next(this.getNewPriceData());
      }

      if (!this.barCliked) {
        this.state = this.state === 'in' ? 'out' : 'in';
      }

      this.barCliked = false;
    }, 8000);
  });

  changePriceData() {
    this.data = this.getNewPriceData();
    this.state = 'in';
    this.barCliked = true;
  }

  getNewPriceData(): TokenCrypto {
    let lenght = this.prices.length;
    if (this.currentDataIndex === lenght) {
      this.currentDataIndex = 0;
    }
    let priceData = this.prices[this.currentDataIndex++];
    if (priceData !== undefined) {
      return this.styleData(priceData);
    }
    return this.emptyTokenCryptoData;
  }

  styleData(data: TokenCrypto): TokenCrypto {
    let priceCurrent: number;
    let percent1h: number;
    let percent24h: number;

    if (data.price.priceCurrent === null) {
      priceCurrent = 0;
    } else {
      priceCurrent = data.price.priceCurrent;
    }

    if (data.price.percentChange1h === null) {
      percent1h = 0;
    } else {
      percent1h = parseFloat(data.price.percentChange1h.toFixed(2));
    }

    if (data.price.percentChange24h === null) {
      percent24h = 0;
    } else {
      percent24h = parseFloat(data.price.percentChange24h.toFixed(2));
    }

    return {
      name: data.name,
      symbol: data.symbol,
      price: {
        priceCurrent: priceCurrent,
        percentChange1h: percent1h,
        percentChange24h: percent24h,
        percentChange7d: data.price.percentChange7d,
        percentChange30d: data.price.percentChange30d,
        percentChange60d: data.price.percentChange60d,
        percentChange90d: data.price.percentChange90d
      }
    };
  }

  getColor(percent: number) {
    return percent < 0 ? '#FF4D00' : '#07F36B';
  }

  mouseoverHandler() {
    clearTimeout(this.closeTimer);
    this.priceBarPopover.open();
    this.closeTimer = setTimeout(() => {
      this.priceBarPopover.close();
    }, 3000);
  }

  mouseoutHandler() {
    this.priceBarPopover.close();
  }

}
