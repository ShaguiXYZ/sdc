import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, Input, signal, WritableSignal } from '@angular/core';
import { DataInfo } from '@shagui/ng-shagui/core';
import { TitleComponent, TooltipComponent, VisualMapComponent } from 'echarts/components';
import type { EChartsCoreOption } from 'echarts/core';
import * as echarts from 'echarts/core';
import { CanvasRenderer } from 'echarts/renderers';
import { NgxEchartsDirective, provideEchartsCore } from 'ngx-echarts';
import { ChartSize, SdcChartSize } from './models';

echarts.use([TitleComponent, TooltipComponent, VisualMapComponent, CanvasRenderer]);

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
    @defer (on viewport; when !!_options()) {
      <div echarts [options]="_options()" [ngStyle]="styleSize"></div>
    } @placeholder (minimum 300ms) {
      <!-- placeholder -->
      <div class="placeholder" [ngStyle]="styleSize"></div>
    } @loading (after 300ms; minimum 1.5s) {
      <div class="sdc-loading" [ngStyle]="styleSize"></div>
    }
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [CommonModule, NgxEchartsDirective],
  providers: [provideEchartsCore({ echarts })]
})
export class SdcEchartComponent {
  public _options: WritableSignal<EChartsCoreOption | null> = signal<EChartsCoreOption | null>(null);
  public styleSize: DataInfo<string | number> = {};

  @Input()
  public set size(value: ChartSize) {
    this.styleSize = new SdcChartSize(value).styleSize;
  }

  @Input()
  public set options(value: EChartsCoreOption) {
    this._options.set(value);
  }
}
