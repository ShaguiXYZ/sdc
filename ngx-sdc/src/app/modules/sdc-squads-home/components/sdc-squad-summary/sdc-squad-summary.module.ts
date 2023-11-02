import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import {
  SdcComponentsStateCountModule,
  SdcCoverageChartModule,
  SdcNoDataModule,
  SdcTimeEvolutionMultichartModule
} from 'src/app/shared/components';
import { SdcSquadSummaryComponent } from './sdc-squad-summary.component';

@NgModule({
  declarations: [SdcSquadSummaryComponent],
  imports: [
    CommonModule,
    NxTabsModule,
    SdcComponentsStateCountModule,
    SdcCoverageChartModule,
    SdcNoDataModule,
    SdcTimeEvolutionMultichartModule,
    TranslateModule
  ],
  exports: [SdcSquadSummaryComponent]
})
export class SdcSquadSummaryModule {}
