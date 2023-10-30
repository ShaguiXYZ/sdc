import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import { SdcCoverageChartModule, SdcHorizontalBarChartModule, SdcNoDataModule } from 'src/app/shared/components';
import { SdcDepartmentSummaryComponent } from './sdc-department-summary.component';

@NgModule({
  declarations: [SdcDepartmentSummaryComponent],
  imports: [CommonModule, SdcCoverageChartModule, NxTabsModule, SdcHorizontalBarChartModule, SdcNoDataModule, TranslateModule],
  exports: [SdcDepartmentSummaryComponent]
})
export class SdcDepartmentSummaryModule {}
