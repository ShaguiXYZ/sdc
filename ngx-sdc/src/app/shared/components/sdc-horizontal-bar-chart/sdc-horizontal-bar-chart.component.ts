import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { BarChart } from 'echarts/charts';
import { GridComponent, TooltipComponent } from 'echarts/components';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { ChartConfig, ChartValue } from './models';
import { TranslateService } from '@ngx-translate/core';
import { TitleCasePipe } from '@angular/common';

@Component({
  selector: 'sdc-horizontal-bar-chart',
  templateUrl: './sdc-horizontal-bar-chart.component.html',
  styleUrls: ['./sdc-horizontal-bar-chart.component.scss'],
  providers: [TitleCasePipe]
})
export class SdcHorizontalBarChartComponent {
  private chartConfig: ChartConfig = { values: [] };

  @Input()
  public name!: string;

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

  constructor(private titleCasePipe: TitleCasePipe, private translateService: TranslateService) {
    this.echartsExtentions = [BarChart, GridComponent, TooltipComponent];
  }

  private chartOptions(chartConfig: ChartConfig): EChartsOption {
    const yAxis =
      chartConfig.values?.map(value => this.titleCasePipe.transform(this.translateService.instant(`Component.State.${value.yAxis}`))) || [];
    const data =
      chartConfig.values?.map(value => ({
        value: value.data || 0,
        itemStyle: {
          color: value.color
        }
      })) || [];

    return {
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
      },
      series: [
        {
          type: 'bar',
          data
        }
      ]
    };
  }
}
