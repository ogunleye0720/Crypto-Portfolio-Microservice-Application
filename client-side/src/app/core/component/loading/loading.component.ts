import { Component } from '@angular/core';
import { LoadingService } from '../../service/loading/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.scss'],
})
export class LoadingComponent {
  isLoading$ = this.loadingService.isLoading$;
  constructor(private loadingService: LoadingService) { }
}
