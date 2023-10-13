import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcCoverageInfoComponent } from './sdc-coverage-info.component';

@NgModule({
  declarations: [SdcCoverageInfoComponent],
  imports: [CommonModule, NxCopytextModule, SdcCoverageChartModule],
  exports: [SdcCoverageInfoComponent]
})
export class SdcCoverageInfoModule {}
