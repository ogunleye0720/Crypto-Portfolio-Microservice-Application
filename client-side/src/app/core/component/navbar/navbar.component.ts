import { Component } from '@angular/core';
import { RouterNavigationService } from 'src/app/core/service/router-navigation/router-navigation.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  constructor(private router: RouterNavigationService) { }

  navigateToHome() {
    this.router.navigateToInputWallet();
  }

}
