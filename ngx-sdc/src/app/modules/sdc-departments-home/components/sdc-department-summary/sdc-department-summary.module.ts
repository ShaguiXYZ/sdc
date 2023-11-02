import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import {
  SdcCoverageChartModule,
  SdcHorizontalBarChartModule,
  SdcNoDataModule,
  SdcTimeEvolutionMultichartModule
} from 'src/app/shared/components';
import { SdcDepartmentSummaryComponent } from './sdc-department-summary.component';

@NgModule({
  declarations: [SdcDepartmentSummaryComponent],
  imports: [
    CommonModule,
    NxHeadlineModule,
    NxTabsModule,
    SdcCoverageChartModule,
    SdcHorizontalBarChartModule,
    SdcNoDataModule,
    SdcTimeEvolutionMultichartModule,
    TranslateModule
  ],
  exports: [SdcDepartmentSummaryComponent]
})
export class SdcDepartmentSummaryModule {}
