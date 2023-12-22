import { Component, Input } from '@angular/core';


@Component({
  selector: 'app-trend-balance-chart',
  templateUrl: './trend-balance-chart.component.html',
  styleUrls: ['./trend-balance-chart.component.scss'],
})
export class TrendBalanceChartComponent {
  @Input() data: number[] = [];
}


