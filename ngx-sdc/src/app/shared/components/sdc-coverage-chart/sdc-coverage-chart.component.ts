import { Component, Input, OnInit } from '@angular/core';
import { ICoverageChartModel } from './models';

import { EChartsOption } from 'echarts';
import { PieChart } from 'echarts/charts';
import { GridComponent, LegendComponent, TooltipComponent } from 'echarts/components';
import { COVERAGE_CHART, COVERAGE_CHART_BACKGROUND, SUMMARY_BACKGROUND } from '../../constants/colors';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent implements OnInit {
  @Input()
  public coverage!: ICoverageChartModel;
  @Input()
  public size!: number;
  @Input()
  public color!: string;

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor() {
    this.echartsExtentions = [PieChart];
  }

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions();
  }

  private chartOptions(): EChartsOption {
    const center = this.size / 2;
    const name = this.coverage.name?.trim()
      ? [`${Math.floor(this.coverage.value)}%`, this.coverage.name].join('\n')
      : `${Math.floor(this.coverage.value)}%`;

    return {
      animation: false,
      series: [
        {
          type: 'pie',
          itemStyle: {
            borderRadius: 15,
            borderColor: SUMMARY_BACKGROUND,
            borderWidth: 2
          },
          radius: ['55%', '70%'],
          center: [center, center],
          // adjust the start angle
          startAngle: 90,
          label: {
            fontSize: 12,
            fontWeight: 'bold',
            show: true,
            position: 'center'
          },
          data: [
            {
              value: this.coverage.value,
              name,
              itemStyle: {
                color: COVERAGE_CHART
              }
            },
            {
              value: 100 - this.coverage.value,
              itemStyle: {
                color: COVERAGE_CHART_BACKGROUND
              },
              label: {
                show: false
              }
            }
          ]
        }
      ]
    };
  }
}
