import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import type { EChartsCoreOption } from 'echarts/core';
import { stringGraphToRecord } from '../lib';
import { ChartSize } from '../models';
import { SdcEchartComponent } from '../sdc-echart.component';

@Component({
  selector: 'sdc-pie-chart',
  template: `<sdc-echart [options]="echartsOptions" [size]="size" />`,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, SdcEchartComponent, NxHeadlineModule]
})
export class SdcPieChartComponent implements OnInit {
  @Input()
  public title?: string;

  @Input()
  public size: ChartSize = {};

  public echartsOptions: EChartsCoreOption = {};

  private _data!: string;

  @Input()
  public set data(value: string) {
    this._data = value;
    this.echartsOptions = this.chartOptions(value);
  }

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions(this._data);
  }

  private chartOptions(chartData: string): EChartsCoreOption {
    const records = stringGraphToRecord(chartData);

    const option: EChartsCoreOption = {
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
          data: Object.entries(records).map(([name, value]) => ({ name, value })),
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

    if (this.title) {
      option['title'] = {
        text: this.title,
        left: 'center'
      };
    }

    return option;
  }
}
