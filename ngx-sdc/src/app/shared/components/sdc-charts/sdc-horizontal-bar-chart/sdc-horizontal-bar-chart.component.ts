import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
import { DataInfo } from 'src/app/core/models';
import { ChartConfig, ChartValue } from 'src/app/shared/models';
import { SdcValueTypeToNumberPipe } from 'src/app/shared/pipes';
import { ChartSize, SdcChartSize } from '../models';

@Component({
  selector: 'sdc-horizontal-bar-chart',
  templateUrl: './sdc-horizontal-bar-chart.component.html',
  styleUrls: ['./sdc-horizontal-bar-chart.component.scss'],
  providers: [SdcValueTypeToNumberPipe, TitleCasePipe],
  standalone: true,
  imports: [CommonModule, NgxEchartsModule]
})
export class SdcHorizontalBarChartComponent {
  @Input()
  public name!: string;
  public styleSize: DataInfo<number | string> = {};
  public echartsOptions: EChartsOption = {};

  @Input()
  set config(value: ChartConfig) {
    this.echartsOptions = this.chartOptions(value);
  }

  @Input()
  public set size(value: ChartSize) {
    this.styleSize = new SdcChartSize(value).styleSize;
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const yAxis = chartConfig.axis?.yAxis;
    const data =
      chartConfig.data
        .map(chartConfigData => chartConfigData.values)
        .filter(values => !Array.isArray(values))
        .map(values => ({
          value: (values as ChartValue).value ?? 0,
          itemStyle: {
            color: (values as ChartValue).color
          }
        })) || [];

    const echartsOptions: EChartsOption = {
      animation: false,
      grid: {
        left: '5%',
        right: '5%',
        bottom: '5%',
        top: '5%',
        containLabel: true
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross'
        }
      },
      yAxis: {
        type: 'category',
        data: yAxis
      },
      xAxis: {
        type: 'value'
      }
    };

    echartsOptions.series = [
      {
        type: 'bar',
        data
      }
    ];

    return echartsOptions;
  }
}
