import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcCoverageChartModule } from '../sdc-coverage-chart/sdc-coverage-chart.module';
import { SdcCoverageInfoComponent } from './sdc-coverage-info.component';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';

@NgModule({
  declarations: [SdcCoverageInfoComponent],
  imports: [CommonModule, NxCopytextModule, SdcCoverageChartModule],
  exports: [SdcCoverageInfoComponent]
})
export class SdcCoverageInfoModule {}
