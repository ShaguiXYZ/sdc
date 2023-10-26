import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcComponentsStateCountModule, SdcCoverageChartModule } from 'src/app/shared/components';
import { SdcSquadSummaryComponent } from './sdc-squad-summary.component';

@NgModule({
  declarations: [SdcSquadSummaryComponent],
  imports: [CommonModule, SdcComponentsStateCountModule, SdcCoverageChartModule],
  exports: [SdcSquadSummaryComponent]
})
export class SdcSquadSummaryModule {}
