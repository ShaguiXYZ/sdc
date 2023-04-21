import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EchartsxModule } from 'echarts-for-angular';
import { SdcHorizontalBarChartComponent } from './sdc-horizontal-bar-chart.component';

@NgModule({
  declarations: [SdcHorizontalBarChartComponent],
  imports: [CommonModule, EchartsxModule],
  exports: [SdcHorizontalBarChartComponent]
})
export class SdcHorizontalBarChartModule {}
