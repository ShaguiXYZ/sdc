import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcSquadCoverageComponent } from './sdc-squad-coverage.component';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';

@NgModule({
  declarations: [SdcSquadCoverageComponent],
  imports: [CommonModule, NxCopytextModule, SdcCoverageChartModule],
  exports: [SdcSquadCoverageComponent]
})
export class SdcSquadCoverageModule {}
