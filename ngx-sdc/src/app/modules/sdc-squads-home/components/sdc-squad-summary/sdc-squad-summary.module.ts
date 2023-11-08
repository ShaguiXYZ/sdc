import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComponentsStateCountModule, SdcNoDataModule, SdcTimeEvolutionMultichartModule } from 'src/app/shared/components';
import { SdcCoverageChartModule } from 'src/app/shared/components/sdc-charts';
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
