import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { LineChart } from 'echarts/charts';
import { GridComponent, TitleComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';

@Component({
  selector: 'sdc-metric-time-evolution-chart',
  templateUrl: './sdc-metric-time-evolution-chart.component.html',
  styleUrls: ['./sdc-metric-time-evolution-chart.component.scss']
})
export class SdcMetricTimeEvolutionChartComponent {
  private _title!: string;
  @Input()
  set title(value: string) {
    this._title = value;
    this.echartsOptions = this.chartOptions(value, this._values);
  }

  private _values: { xAxis: string; data: number }[] = [];
  @Input()
  set values(value: { xAxis: string; data: number }[]) {
    this._values = value || [];
    this.echartsOptions = this.chartOptions(this._title, this._values);
  }

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor() {
    this.echartsExtentions = [LineChart, GridComponent, TitleComponent, TooltipComponent, VisualMapComponent];
  }

  private chartOptions(title?: string, values?: { xAxis: string; data: number }[]): EChartsOption {
    console.log('Metric chart values', values);

    const xAxis = values?.map(value => value.xAxis) || [];
    const data = values?.map(value => value.data) || [];

    return {
      animation: false,
      title: {
        text: title
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        }
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: xAxis
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '{value} W'
        },
        axisPointer: {
          snap: true
        }
      },
      visualMap: {
        show: false,
        dimension: 0,
        pieces: [
          {
            lte: 6,
            color: 'green'
          },
          {
            gt: 6,
            lte: 8,
            color: 'red'
          },
          {
            gt: 8,
            lte: 14,
            color: 'green'
          },
          {
            gt: 14,
            lte: 17,
            color: 'red'
          },
          {
            gt: 17,
            color: 'green'
          }
        ]
      },
      series: [
        {
          name: title,
          type: 'line',
          smooth: true,
          data
        }
      ]
    };
  }
}
