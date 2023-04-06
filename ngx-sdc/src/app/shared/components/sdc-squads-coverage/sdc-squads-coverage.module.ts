import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { TranslateModule } from '@ngx-translate/core';
import { SdcSquadCoverageModule } from '../sdc-squad-coverage/sdc-squad-coverage.module';
import { SdcSquadsCoverageComponent } from './sdc-squads-coverage.component';

@NgModule({
  declarations: [SdcSquadsCoverageComponent],
  imports: [
    CommonModule,
    FormsModule,
    NxCardModule,
    NxHeadlineModule,
    NxIconModule,
    NxInputModule,
    SdcSquadCoverageModule,
    TranslateModule
  ],
  exports: [SdcSquadsCoverageComponent]
})
export class SdcSquadsCoverageModule {}
