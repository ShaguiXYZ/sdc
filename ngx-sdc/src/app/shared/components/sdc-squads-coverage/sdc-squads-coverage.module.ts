import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { SdcSquadCoverageModule } from '../sdc-squad-coverage/sdc-squad-coverage.module';
import { SdcSquadsCoverageComponent } from './sdc-squads-coverage.component';

@NgModule({
  declarations: [SdcSquadsCoverageComponent],
  imports: [CommonModule, NxCardModule, SdcSquadCoverageModule],
  exports: [SdcSquadsCoverageComponent]
})
export class SdcSquadsCoverageModule {}
