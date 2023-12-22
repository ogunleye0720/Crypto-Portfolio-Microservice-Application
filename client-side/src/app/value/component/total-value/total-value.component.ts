import { Component, OnInit } from '@angular/core';
import { TotalValueService } from '../../service/total-value.service';
import { TotalValues } from '../../model/total-values';

@Component({
  selector: 'app-total-value',
  templateUrl: './total-value.component.html',
  styleUrls: ['./total-value.component.scss']
})
export class TotalValueComponent implements OnInit {

  public totalValueData: TotalValues;
  switchNumber: number = 0;
  trendData: number[] = [0, 0];

  constructor(private totalValueService: TotalValueService) { }

  ngOnInit(): void {
    this.totalValueService.currentTotalValue$.subscribe(requestData => {
      this.totalValueData = requestData;
      this.trendData = [
        this.totalValueData.value7d,
        this.totalValueData.value24h,
        this.totalValueData.value1h,
        this.totalValueData.totalValue];
    });
  }

  getColor(value: number) {
    return value < 0 ? '#FF4D00' : '#07F36B';
  }

  changeTotalValueDataTime() {
    if (this.switchNumber === 0) {
      this.switchNumber = 1;
    } else if (this.switchNumber === 1) {
      this.switchNumber = 2;
    } else if (this.switchNumber === 2) {
      this.switchNumber = 0;
    }
  }

}