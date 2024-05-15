import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';
import { DataInfo } from '@shagui/ng-shagui/core';
import { EChartsOption } from 'echarts';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { MetricState, stateByCoverage } from 'src/app/shared/lib';
import { SdcEchartComponent } from '../sdc-echart.component';

@Component({
  selector: 'sdc-coverage-chart',
  template: `<sdc-echart [options]="echartsOptions" [size]="size" />`,
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [CommonModule, SdcEchartComponent]
})
export class SdcCoverageChartComponent implements OnInit {
  @Input()
  public animation = false;

  @Input()
  public size!: number;

  @Input()
  public backgroundColor!: string;

  public echartsOptions: EChartsOption = {};

  private _coverage!: ICoverageModel;

  ngOnInit(): void {
    this.echartsOptions = this.chartOptions(this._coverage);
  }

  @Input()
  public set coverage(value: ICoverageModel) {
    this._coverage = value;
    this.echartsOptions = this.chartOptions(value);
  }

  public get styleSize(): DataInfo<number> {
    return { 'height.px': this.size, 'width.px': this.size };
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
