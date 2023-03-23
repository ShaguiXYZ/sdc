import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxMessageModule } from '@aposin/ng-aquila/message';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { TranslateModule } from '@ngx-translate/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import {
  SdcComplianceBarCardComponent,
  SdcComplianceBarCardsComponent,
  SdcComponentsStateCountComponent,
  SdcCoverageChartComponent,
  SdcSquadCoverageComponent,
  SdcSquadsCoverageComponent,
  SdcSquadSummaryComponent,
  SdcStateCountComponent
} from './components';

@NgModule({
  declarations: [
    SdcComplianceBarCardComponent,
    SdcComplianceBarCardsComponent,
    SdcCoverageChartComponent,
    SdcComponentsStateCountComponent,
    SdcSquadCoverageComponent,
    SdcSquadSummaryComponent,
    SdcSquadsCoverageComponent,
    SdcStateCountComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    NgxChartsModule,
    NxBadgeModule,
    NxButtonModule,
    NxCardModule,
    NxCopytextModule,
    NxGridModule,
    NxHeadlineModule,
    NxIconModule,
    NxLinkModule,
    NxMessageModule,
    NxModalModule.forRoot(),
    NxProgressbarModule,
    NxSpinnerModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  exports: [SdcComplianceBarCardsComponent, SdcSquadSummaryComponent, SdcSquadsCoverageComponent]
})
export class AppSharedModule {}
