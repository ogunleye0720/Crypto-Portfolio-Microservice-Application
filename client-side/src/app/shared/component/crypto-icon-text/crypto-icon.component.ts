import { Component, Input, OnInit } from '@angular/core';

interface IconUrls {
  [key: string]: string;
}

@Component({
  selector: 'app-crypto-icon',
  templateUrl: './crypto-icon.component.html',
  styleUrls: ['./crypto-icon.component.scss']
})
export class CryptoIconComponent {

  @Input() text: string = '';

  iconUrls: IconUrls = {
    'Ethereum': '/assets/icons/ethereum.svg',
    'Bitcoin': '/assets/icons/bitcoin.svg',
    'Avalanche': '/assets/icons/avalanche-avax-logo.svg',
    'Solana': '/assets/icons/solana-sol-logo.svg',
    'Polygon': '/assets/icons/polygon-matic-logo.svg',
    'BNB': '/assets/icons/bnb-bnb-logo.svg',
    'Cardano': '/assets/icons/cardano-ada-logo.svg',
    'Aptos': '/assets/icons/aptos-apt-logo.svg',
    'Chia-Network': '/assets/icons/chia-network-xch-logo.svg',
    'Cosmos': '/assets/icons/cosmos-atom-logo.svg',
  };

  get iconUrl(): string {
    return this.iconUrls[this.text];
  }
}
