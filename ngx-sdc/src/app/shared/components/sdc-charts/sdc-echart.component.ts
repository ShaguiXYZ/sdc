import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { EChartsOption } from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
import { DataInfo } from 'src/app/core/models';
import { ChartSize, SdcChartSize } from './models';

@Component({
  selector: 'sdc-echart',
  template: `
    @defer () {
    <div echarts [options]="options" [ngStyle]="styleSize"></div>
    } @placeholder (minimum 300ms) {
    <!-- placeholder -->
    } @loading (after 300ms; minimum 1.5s) {
    <div class="placeholder" [ngStyle]="styleSize"></div>
    }
  `,
  styles: [
    `
      :host {
        display: block;
        height: 100%;
        width: 100%;
      }

      .placeholder {
        background-image: url('/assets/images/loading-spinner.svg');
        background-position: center;
        background-repeat: no-repeat;
        height: 100%;
        width: 100%;
      }
    `
  ],
  standalone: true,
  imports: [CommonModule, NgxEchartsModule]
})
export class SdcEchartComponent {
  public styleSize: DataInfo<string | number> = {};

  @Input()
  public options: EChartsOption = {};

  @Input()
  public set size(value: ChartSize) {
    this.styleSize = new SdcChartSize(value).styleSize;
  }
}
