import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { EchartsxModule } from 'echarts-for-angular';
import { SdcTimeEvolutionChartComponent } from './sdc-time-evolution-chart.component';

@NgModule({
  declarations: [SdcTimeEvolutionChartComponent],
  imports: [CommonModule, EchartsxModule],
  exports: [SdcTimeEvolutionChartComponent]
})
export class SdcTimeEvolutionChartModule {}
