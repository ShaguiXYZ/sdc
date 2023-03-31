import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EchartsxModule } from 'echarts-for-angular';
import { SdcCoverageChartComponent } from './sdc-coverage-chart.component';

@NgModule({
  declarations: [SdcCoverageChartComponent],
  imports: [CommonModule, EchartsxModule],
  exports: [SdcCoverageChartComponent]
})
export class SdcCoverageChartModule {}
