import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { _CHART_ID_ } from 'src/app/core/services/sdc';
import { ChartConfig, ChartData, ChartValue } from '../../models';
import { SdcValueTypeToNumberPipe } from '../../pipes/sdc-value-type-to-number.pipe';

@Component({
  selector: 'sdc-time-evolution-chart',
  templateUrl: './sdc-time-evolution-chart.component.html',
  styleUrls: ['./sdc-time-evolution-chart.component.scss'],
  providers: [SdcValueTypeToNumberPipe]
})
export class SdcTimeEvolutionChartComponent {
  public chartId = _CHART_ID_();
  public echartsOptions: EChartsOption = {};
  public styleSize: GenericDataInfo<number> = {};

  constructor(private readonly valueTypeToNumberPipe: SdcValueTypeToNumberPipe) {}

  @Input()
  set config(value: ChartConfig) {
    this.echartsOptions = this.chartOptions({ ...value });
  }

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
          smooth: data.smooth,
          data: serie.map(item => ({
            sourceData: item.value,
            value: this.valueTypeToNumberPipe.transform(item.value, chartConfig.type) ?? 0
          }))
        }
      };
    });

    const options: GenericDataInfo<any> = {
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

    if (chartConfig.options?.showLegend) {
      echartsOptions.legend = {
        orient: 'horizontal',
        bottom: -5
      };
    }

    return echartsOptions;
  }
}
