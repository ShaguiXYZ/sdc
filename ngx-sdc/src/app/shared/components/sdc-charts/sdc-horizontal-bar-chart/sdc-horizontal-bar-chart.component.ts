import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { ChartConfig, ChartSize, ChartValue } from '../models';
import { SdcEchartComponent } from '../sdc-echart.component';

@Component({
  selector: 'sdc-horizontal-bar-chart',
  template: `<sdc-echart [options]="echartsOptions" [size]="size" />`,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [CommonModule, SdcEchartComponent]
})
export class SdcHorizontalBarChartComponent {
  @Input()
  public name!: string;

  @Input()
  public size: ChartSize = {};

  public echartsOptions: EChartsOption = {};

  @Input()
  set config(value: ChartConfig) {
    this.echartsOptions = this.chartOptions(value);
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
