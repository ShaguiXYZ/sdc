import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcComponentsStateCountModule } from '../sdc-components-state-count/sdc-components-state-count.module';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcDepartmentSummaryComponent } from './sdc-department-summary.component';

@NgModule({
  declarations: [SdcDepartmentSummaryComponent],
  imports: [CommonModule, SdcComponentsStateCountModule, SdcCoverageChartModule],
  exports: [SdcDepartmentSummaryComponent]
})
export class SdcDepartmentSummaryModule {}
