import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { BarChart } from 'echarts/charts';
import { GridComponent, TooltipComponent } from 'echarts/components';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { ChartConfig, ChartValue } from './models';

@Component({
  selector: 'sdc-horizontal-bar-chart',
  templateUrl: './sdc-horizontal-bar-chart.component.html',
  styleUrls: ['./sdc-horizontal-bar-chart.component.scss']
})
export class SdcHorizontalBarChartComponent {
  private chartConfig: ChartConfig = { values: [] };

  @Input()
  set name(value: string) {
    this.chartConfig = { ...this.chartConfig, name: value };
    this.echartsOptions = this.chartOptions(this.chartConfig);
  }

  @Input()
  set values(value: ChartValue[]) {
    this.chartConfig = { ...this.chartConfig, values: value || [] };
    this.echartsOptions = this.chartOptions(this.chartConfig);
  }

  public styleSize: GenericDataInfo<number> = {};
  @Input()
  public set size(value: { height?: number; width?: number }) {
    delete this.styleSize['height.px'];
    if (value.height) {
      this.styleSize['height.px'] = value.height;
    }

    delete this.styleSize['width.px'];
    if (value.width) {
      this.styleSize['width.px'] = value.width;
    }
  }

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor() {
    this.echartsExtentions = [BarChart, GridComponent, TooltipComponent];
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const yAxis = chartConfig.values?.map(value => value.yAxis) || [];
    const data = chartConfig.values?.map(value => value.data || 0) || [];

    return {
      animation: false,
      grid: {
        left: '3%',
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
        boundaryGap: false,
        data: yAxis
      },
      xAxis: {
        type: 'value',
        boundaryGap: false
      },
      series: [
        {
          name: chartConfig.name,
          type: 'bar',
          data
        }
      ]
    };
  }
}
