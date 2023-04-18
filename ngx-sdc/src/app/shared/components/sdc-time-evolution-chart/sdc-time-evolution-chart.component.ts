import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { LineChart } from 'echarts/charts';
import { GridComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { SdcValueTypeToNumberPipe } from '../../pipes/sdc-value-type-to-number.pipe';
import { ChartConfig, ChartValue } from './models';

@Component({
  selector: 'sdc-time-evolution-chart',
  templateUrl: './sdc-time-evolution-chart.component.html',
  styleUrls: ['./sdc-time-evolution-chart.component.scss'],
  providers: [SdcValueTypeToNumberPipe]
})
export class SdcTimeEvolutionChartComponent {
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

  constructor(private valueTypeToNumberPipe: SdcValueTypeToNumberPipe) {
    this.echartsExtentions = [LineChart, GridComponent, TooltipComponent, VisualMapComponent];
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const xAxis = chartConfig.values?.map(value => value.xAxis) || [];
    const data = chartConfig.values?.map(value => this.valueTypeToNumberPipe.transform(value.data, value.type) || 0) || [];
    const pieces =
      chartConfig.values
        ?.map((value, index) => ({
          gte: index,
          lt: index + 1,
          color: value.color
        }))
        .filter(value => value.color) || [];

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
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: xAxis
      },
      yAxis: {
        type: 'value',
        axisLabel: {
          formatter: '{value}'
        },
        axisPointer: {
          snap: true
        }
      },
      visualMap: {
        show: false,
        dimension: 0,
        pieces
      },
      series: [
        {
          name: chartConfig.name,
          type: 'line',
          smooth: false,
          symbolSize: 15,
          data
        }
      ]
    };
  }
}
