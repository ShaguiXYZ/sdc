import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { SdcCoverageInfoComponent } from './sdc-coverage-info.component';

@NgModule({
  declarations: [SdcCoverageInfoComponent],
  imports: [CommonModule, NxCopytextModule],
  exports: [SdcCoverageInfoComponent]
})
export class SdcCoverageInfoModule {}
