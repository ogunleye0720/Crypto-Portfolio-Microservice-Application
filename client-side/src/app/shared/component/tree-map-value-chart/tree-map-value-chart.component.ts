import { Component, ElementRef, ViewChild } from '@angular/core';
import { TokenTreeChartDataService } from '../../services/token-tree-chart-data.service';
import { TokenChartTreeData } from '../../model/TokenChartTreeData';
import { tap } from 'rxjs';

@Component({
  selector: 'app-tree-map-value-chart',
  templateUrl: './tree-map-value-chart.component.html',
  styleUrls: ['./tree-map-value-chart.component.scss']
})
export class TreeMapValueChartComponent {
  view: [number, number] = [100, 100];
  multi = [
    {
      name: "ETH",
      value: 0
    },
    {
      name: "MATIC",
      value: 0
    },
    {
      name: "AVAX",
      value: 0
    }
  ];

  gradient = false;
  showLegend = false;
  public legendTitle = 'Legend';
  animations = true;
  customColor =
    [
      { name: "ETH", value: '#393939' },
      { name: "MATIC", value: '#7e45de' },
      { name: "AVAX", value: '#e84142' },
    ]

  @ViewChild('treeMapChartContainer') chartContainer!: ElementRef;
  dataTemp: TokenChartTreeData[] = [];
  public isMultiDataAvailable:boolean = false;

  constructor(private tokenTreeChartDataService: TokenTreeChartDataService) { }

  ngOnInit() {
    this.tokenTreeChartDataService.data$.pipe(
      tap(data => {
        if (data.length === 3) {
          this.multi = data;
          this.isMultiDataAvailable = this.setMultiDataAvailable();
        }
      })
    ).subscribe();

    setTimeout(() => {
      this.setView(5);

    });
  }

  onResize() {
    setTimeout(() => {
      this.setView();
    });
  }

  setView(offsetWidth: number = 0) {
    const containerWidth = this.chartContainer.nativeElement.offsetWidth;
    const containerHeight = this.chartContainer.nativeElement.offsetHeight;
    if (offsetWidth > 0) {
      this.view = [containerWidth - offsetWidth, containerHeight];
    } else {
      this.view = [containerWidth + 2, containerHeight];
    }
  }

  public setMultiDataAvailable(): boolean {
    return this.multi.every(item => item.value === 0);
  }

}
