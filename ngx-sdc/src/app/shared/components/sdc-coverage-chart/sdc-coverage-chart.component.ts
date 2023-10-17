import { Component, Input, OnInit } from '@angular/core';

import { EChartsOption } from 'echarts';
import { PieChart } from 'echarts/charts';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { MetricState, stateByCoverage } from '../../lib';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent implements OnInit {
  private _coverage!: ICoverageModel;

  @Input()
  public animation = false;
  @Input()
  public size!: number;
  @Input()
  public backgroundColor!: string;

  public readonly echartsExtentions: any[];
  public echartsOptions: EChartsOption = {};

  constructor() {
    this.echartsExtentions = [PieChart];
  }

  @Input()
  public set coverage(value: ICoverageModel) {
    this._coverage = value;
    this.echartsOptions = this.chartOptions(value);
  }

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions(this._coverage);
  }

  private chartOptions(value: ICoverageModel): EChartsOption {
    const center = this.size / 2;
    const coverage = value.coverage ?? 0;
    const name = value.name.trim() ? [`${Math.floor(coverage)}%`, value.name].join('\n') : `${Math.floor(coverage)}%`;
    const color = MetricState[stateByCoverage(coverage)].color;

    const option: EChartsOption = {
      animation: this.animation,
      series: [
        {
          type: 'pie',
          itemStyle: {
            borderRadius: 15,
            borderColor: 'transparent',
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
                color: this.backgroundColor
              },
              label: {
                show: false
              }
            }
          ]
        }
      ]
    };

    return option;
  }
}
