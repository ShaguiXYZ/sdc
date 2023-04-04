import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EchartsxModule } from 'echarts-for-angular';
import { SdcMetricTimeEvolutionChartComponent } from './sdc-metric-time-evolution-chart.component';

@NgModule({
  declarations: [SdcMetricTimeEvolutionChartComponent],
  imports: [CommonModule, EchartsxModule],
  exports: [SdcMetricTimeEvolutionChartComponent]
})
export class SdcMetricTimeEvolutionChartModule {}
