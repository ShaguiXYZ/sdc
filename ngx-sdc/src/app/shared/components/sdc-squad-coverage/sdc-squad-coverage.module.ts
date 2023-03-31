import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcSquadCoverageComponent } from './sdc-squad-coverage.component';

@NgModule({
  declarations: [SdcSquadCoverageComponent],
  imports: [CommonModule, SdcCoverageChartModule],
  exports: [SdcSquadCoverageComponent]
})
export class SdcSquadCoverageModule {}
