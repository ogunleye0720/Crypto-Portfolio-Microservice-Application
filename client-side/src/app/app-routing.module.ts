import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BalanceTableComponent } from './balance/component/balance-table/balance-table.component';
import { InputWalletsComponent } from './wallet/input-wallets/input-wallets.component';

const routes: Routes = [
  { path: '', redirectTo: 'wallet', pathMatch: 'full' },
  { path: 'wallet', component: InputWalletsComponent },
  { path: 'balance', component: BalanceTableComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
