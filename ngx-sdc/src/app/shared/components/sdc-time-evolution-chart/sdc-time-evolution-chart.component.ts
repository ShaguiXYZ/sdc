import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { LineChart } from 'echarts/charts';
import { GridComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { SdcValueTypeToNumberPipe } from '../../pipes/sdc-value-type-to-number.pipe';
import { ChartConfig, ChartData, ChartValue } from '../../models';

@Component({
  selector: 'sdc-time-evolution-chart',
  templateUrl: './sdc-time-evolution-chart.component.html',
  styleUrls: ['./sdc-time-evolution-chart.component.scss'],
  providers: [SdcValueTypeToNumberPipe]
})
export class SdcTimeEvolutionChartComponent {
  @Input()
  set config(value: ChartConfig) {
    this.echartsOptions = this.chartOptions({ ...value });
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

  @Input()
  public showVisualMap = true;

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor(private readonly valueTypeToNumberPipe: SdcValueTypeToNumberPipe) {
    this.echartsExtentions = [LineChart, GridComponent, TooltipComponent, VisualMapComponent];
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const xAxis: string[] = chartConfig.axis.xAxis ?? [];
    const chartData: ChartData[] = chartConfig.data.filter(data => Array.isArray(data.values));

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
          data: serie.map(item => ({
            sourceData: item.value,
            value: this.valueTypeToNumberPipe.transform(item.value, chartConfig.type) ?? 0
          }))
        }
      };
    });

    const echartsOptions: EChartsOption = {
      animation: false,
      grid: {
        left: '3%',
        right: '5%',
        bottom: '5%',
        top: '5%',
        containLabel: true
      },
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

    if (this.showVisualMap) {
      echartsOptions.visualMap = chartDataConfig.map(dataConfig => dataConfig.visualMap);
    }

    echartsOptions.series = chartDataConfig.map(dataConfig => ({
      data: dataConfig.serie.data,
      lineStyle: {
        type: dataConfig.serie.lineStyle
      },
      name: dataConfig.serie.name,
      smooth: false,
      symbolSize: 12,
      type: 'line'
    }));

    return echartsOptions;
  }
}
