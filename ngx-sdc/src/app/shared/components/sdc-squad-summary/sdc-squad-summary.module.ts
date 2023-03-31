import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcComponentsStateCountModule } from '../sdc-components-state-count/sdc-components-state-count.module';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcSquadSummaryComponent } from './sdc-squad-summary.component';

@NgModule({
  declarations: [SdcSquadSummaryComponent],
  imports: [CommonModule, SdcComponentsStateCountModule, SdcCoverageChartModule],
  exports: [SdcSquadSummaryComponent]
})
export class SdcSquadSummaryModule {}
