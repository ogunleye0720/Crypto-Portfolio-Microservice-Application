import { Component } from '@angular/core';
import { RouterNavigationService } from 'src/app/core/service/router-navigation/router-navigation.service';
import { InputWalletsDataService } from 'src/app/shared/services/input-wallets-data.service';

@Component({
  selector: 'app-input-wallets',
  templateUrl: './input-wallets.component.html',
  styleUrls: ['./input-wallets.component.scss']
})
export class InputWalletsComponent {
  wallets: string | undefined;

  constructor(private router: RouterNavigationService, private data: InputWalletsDataService) { }

  ngOnInit(): void {
    const walletsData = this.data.getDataFromSessionStorage();
    if (walletsData != '') {
      this.wallets = walletsData;
    }
  }

  public getUserWallets(wallets: any) {
    this.data.storeDataInSessionStorage(wallets);
    this.router.navigateToBalance();
  }

}
