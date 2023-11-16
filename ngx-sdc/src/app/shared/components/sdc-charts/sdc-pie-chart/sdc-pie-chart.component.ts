import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { EChartsOption } from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
import { GenericDataInfo } from 'src/app/core/models';
import { stringGraphToDataInfo } from '../lib';

@Component({
  selector: 'sdc-pie-chart',
  templateUrl: './sdc-pie-chart.component.html',
  styleUrls: ['./sdc-pie-chart.component.scss'],
  standalone: true,
  imports: [CommonModule, NgxEchartsModule, NxHeadlineModule]
})
export class SdcPieChartComponent implements OnInit {
  @Input()
  public title?: string;

  @Input()
  public size!: number;

  public echartsOptions: EChartsOption = {};

  private _data!: string;

  @Input()
  public set data(value: string) {
    this._data = value;
    this.echartsOptions = this.chartOptions(value);
  }

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions(this._data);
  }

  public get styleSize(): GenericDataInfo<number> {
    return { 'height.px': this.size, 'width.px': this.size };
  }

  private chartOptions(value: string): EChartsOption {
    const dataInfo = stringGraphToDataInfo(value);

    const option: EChartsOption = {
      grid: {
        left: '2%',
        right: '2%',
        bottom: '5%',
        top: '5%',
        containLabel: true
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b}: ({d}%)'
      },
      series: [
        {
          type: 'pie',
          radius: '50%',
          data: Object.entries(dataInfo).map(([name, value]) => ({ name, value })),
          emphasis: {
            focus: 'self',
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          label: {
            formatter: '{b}: ({d}%)'
          }
        }
      ]
    };

    return option;
  }
}
