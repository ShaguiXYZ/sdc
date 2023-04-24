import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { EchartsxModule } from 'echarts-for-angular';
import { SdcHorizontalBarChartComponent } from './sdc-horizontal-bar-chart.component';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';

@NgModule({
  declarations: [SdcHorizontalBarChartComponent],
  imports: [CommonModule, EchartsxModule, NxHeadlineModule, TranslateModule],
  exports: [SdcHorizontalBarChartComponent]
})
export class SdcHorizontalBarChartModule {}
