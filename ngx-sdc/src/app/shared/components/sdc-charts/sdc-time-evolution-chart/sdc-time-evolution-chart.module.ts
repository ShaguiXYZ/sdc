import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgxEchartsModule } from 'ngx-echarts';
import { SdcTimeEvolutionChartComponent } from './sdc-time-evolution-chart.component';

@NgModule({
  declarations: [SdcTimeEvolutionChartComponent],
  imports: [
    CommonModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })
  ],
  exports: [SdcTimeEvolutionChartComponent]
})
export class SdcTimeEvolutionChartModule {}
