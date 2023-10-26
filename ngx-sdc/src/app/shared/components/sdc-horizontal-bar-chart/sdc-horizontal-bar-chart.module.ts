import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NgxEchartsModule } from 'ngx-echarts';
import { SdcHorizontalBarChartComponent } from './sdc-horizontal-bar-chart.component';

@NgModule({
  declarations: [SdcHorizontalBarChartComponent],
  imports: [
    CommonModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    }),
    NxHeadlineModule
  ],
  exports: [SdcHorizontalBarChartComponent]
})
export class SdcHorizontalBarChartModule {}
