import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { SdcCoverageChartComponent } from './sdc-coverage-chart.component';

@NgModule({
  declarations: [SdcCoverageChartComponent],
  imports: [
    CommonModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  exports: [SdcCoverageChartComponent]
})
export class SdcCoverageChartModule {}
