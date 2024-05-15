import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { DataInfo } from '@shagui/ng-shagui/core';
import { EChartsOption } from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
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

      .placeholder {
        height: 100%;
        width: 100%;
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
      <div class="sdc-loading" [ngStyle]="styleSize"></div>
    }
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
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
