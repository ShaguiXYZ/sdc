import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { DataInfo } from 'src/app/core/models';
import { legendPosition } from '../lib';
import { ChartConfig, ChartData, ChartSize, ChartValue } from '../models';
import { SdcEchartComponent } from '../sdc-echart.component';

@Component({
  selector: 'sdc-time-evolution-chart',
  template: `<sdc-echart [options]="echartsOptions" [size]="size" />`,
  standalone: true,
  imports: [CommonModule, SdcEchartComponent]
})
export class SdcTimeEvolutionChartComponent {
  public echartsOptions: EChartsOption = {};

  @Input()
  public size: ChartSize = {};

  @Input()
  set config(value: ChartConfig) {
    this.echartsOptions = this.chartOptions(value);
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const xAxis: string[] = chartConfig.axis.xAxis ?? [];
    const chartData: ChartData[] = chartConfig.data.filter(data => Array.isArray(data.values));
    const legendPos = chartConfig.options?.legendPosition;

    const chartDataConfig = chartData.map((data, index) => {
      const serie: ChartValue[] = data.values as ChartValue[];

      return {
        visualMap: {
          show: false,
          dimension: 0,
          seriesIndex: index,
          pieces: serie.map((item, pieceIndex) => ({
            gte: pieceIndex,
            lt: pieceIndex + 1,
            color: item.color
          }))
        },
        serie: {
          name: data.name ?? '',
          lineStyle: data.lineStyle ?? 'solid',
          smooth: data.smooth,
          data: serie.map(item => ({
            sourceData: item.value,
            value: item.value ?? 0
          }))
        }
      };
    });

    let options: DataInfo<any> = {
      animation: false,
      tooltip: {
        axisPointer: {
          type: 'cross'
        },
        trigger: 'axis'
      },
      xAxis: {
        axisPointer: {},
        type: 'category',
        boundaryGap: false,
        data: xAxis
      },
      yAxis: {
        axisPointer: {
          snap: true
        },
        type: 'value',
        axisLabel: {
          formatter: '{value}'
        }
      }
    };

    options = Object.assign(options, legendPosition(legendPos));

    if (chartConfig.options?.showVisualMap) {
      options['visualMap'] = chartDataConfig.map(dataConfig => dataConfig.visualMap);
    }

    const echartsOptions: EChartsOption = options;

    echartsOptions.series = chartDataConfig.map(dataConfig => ({
      data: dataConfig.serie.data,
      lineStyle: {
        type: dataConfig.serie.lineStyle
      },
      name: dataConfig.serie.name,
      smooth: dataConfig.serie.smooth,
      symbolSize: 12,
      type: 'line'
    }));

    return echartsOptions;
  }
}
