import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { TranslateModule } from '@ngx-translate/core';
import { SdcCoverageInfoModule } from '../sdc-coverage-info/sdc-coverage-info.module';
import { SdcCoveragesComponent } from './sdc-coverages.component';

@NgModule({
  declarations: [SdcCoveragesComponent],
  imports: [
    CommonModule,
    FormsModule,
    NxCardModule,
    NxHeadlineModule,
    NxIconModule,
    NxInputModule,
    SdcCoverageInfoModule,
    TranslateModule
  ],
  exports: [SdcCoveragesComponent]
})
export class SdcCoveragesModule {}
