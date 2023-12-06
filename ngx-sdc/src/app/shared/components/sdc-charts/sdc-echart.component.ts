import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { EChartsOption } from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
import { DataInfo } from 'src/app/core/models';
import { ChartSize, SdcChartSize } from './models';

@Component({
  selector: 'sdc-echart',
  styles: [
    `
      :host {
        display: block;
        height: 100%;
        width: 100%;
      }

      .loading,
      .placeholder {
        height: 100%;
        width: 100%;
      }

      .loading {
        background-image: url('/assets/images/loading-spinner.svg');
        background-position: center;
        background-repeat: no-repeat;
      }
    `
  ],
  template: `
    @defer {
      <div echarts [options]="options" [ngStyle]="styleSize"></div>
    } @placeholder (minimum 300ms) {
      <!-- placeholder -->
      <div class="placeholder" [ngStyle]="styleSize"></div>
    } @loading (after 300ms; minimum 1.5s) {
      <div class="loading" [ngStyle]="styleSize"></div>
    }
  `,
  standalone: true,
  imports: [CommonModule, NgxEchartsModule]
})
export class SdcEchartComponent {
  public styleSize: DataInfo<string | number> = {};

  @Input()
  public options: EChartsOption = {};

  @Output()
  public onClick: EventEmitter<any> = new EventEmitter();

  @Input()
  public set size(value: ChartSize) {
    this.styleSize = new SdcChartSize(value).styleSize;
  }
}
