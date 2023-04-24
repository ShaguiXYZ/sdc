import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcHorizontalBarChartModule } from '../sdc-horizontal-bar-chart/sdc-horizontal-bar-chart.module';
import { SdcDepartmentSummaryComponent } from './sdc-department-summary.component';

@NgModule({
  declarations: [SdcDepartmentSummaryComponent],
  imports: [CommonModule, SdcCoverageChartModule, SdcHorizontalBarChartModule, TranslateModule],
  exports: [SdcDepartmentSummaryComponent]
})
export class SdcDepartmentSummaryModule {}
