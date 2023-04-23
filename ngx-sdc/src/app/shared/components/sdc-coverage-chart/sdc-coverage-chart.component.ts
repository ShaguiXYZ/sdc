import { Component, Input, OnInit } from '@angular/core';

import { EChartsOption } from 'echarts';
import { PieChart } from 'echarts/charts';
import { MetricState, stateByCoverage } from 'src/app/core/lib';
import { COVERAGE_CHART_BACKGROUND, SUMMARY_BACKGROUND } from '../../constants/colors';
import { ICoverageModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent implements OnInit {
  private _coverage!: ICoverageModel;
  @Input()
  public set coverage(value: ICoverageModel) {
    this._coverage = value;
    this.echartsOptions = this.chartOptions(value);
  }

  @Input()
  public animation = false;
  @Input()
  public size!: number;

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor() {
    this.echartsExtentions = [PieChart];
  }

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions(this._coverage);
  }

  private chartOptions(value: ICoverageModel): EChartsOption {
    const center = this.size / 2;
    const coverage = value.coverage || 0;
    const name = value.name?.trim() ? [`${Math.floor(coverage || 0)}%`, value.name].join('\n') : `${Math.floor(coverage)}%`;
    const color = MetricState[stateByCoverage(coverage)].color;

    return {
      animation: this.animation,
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
              value: coverage,
              name,
              itemStyle: {
                color
              }
            },
            {
              value: 100 - coverage,
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
