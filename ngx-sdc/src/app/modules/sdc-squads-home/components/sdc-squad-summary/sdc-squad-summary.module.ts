import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComponentsStateCountModule, SdcCoverageChartModule, SdcNoDataModule } from 'src/app/shared/components';
import { SdcSquadSummaryComponent } from './sdc-squad-summary.component';

@NgModule({
  declarations: [SdcSquadSummaryComponent],
  imports: [CommonModule, SdcComponentsStateCountModule, NxTabsModule, SdcCoverageChartModule, SdcNoDataModule, TranslateModule],
  exports: [SdcSquadSummaryComponent]
})
export class SdcSquadSummaryModule {}
