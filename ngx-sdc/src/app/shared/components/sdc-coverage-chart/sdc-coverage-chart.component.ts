import { Component, Input, OnInit } from '@angular/core';
import { ICoverageChartModel } from './models';

import { EChartsOption } from 'echarts';
import { PieChart } from 'echarts/charts';
import { COVERAGE_CHART, COVERAGE_CHART_BACKGROUND, SUMMARY_BACKGROUND } from '../../constants/colors';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent implements OnInit {
  private _coverage!: ICoverageChartModel;
  @Input()
  public set coverage(value: ICoverageChartModel) {
    this._coverage = value;
    this.echartsOptions = this.chartOptions(value);
  }

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
    this.echartsOptions = this.chartOptions(this._coverage);
  }

  private chartOptions(value: ICoverageChartModel): EChartsOption {
    const center = this.size / 2;
    const name = value.name?.trim() ? [`${Math.floor(value.value)}%`, value.name].join('\n') : `${Math.floor(value.value)}%`;

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
              value: value.value,
              name,
              itemStyle: {
                color: COVERAGE_CHART
              }
            },
            {
              value: 100 - value.value,
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
