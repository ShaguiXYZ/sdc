import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule, SdcCoveragesModule } from 'src/app/shared/components';
import { SdcSquadSummaryModule } from './components';
import { SdcSquadsHomeRoutingModule } from './sdc-squads-home-routing.module';
import { SdcSquadsHomeComponent } from './sdc-squads-home.component';

@NgModule({
  declarations: [SdcSquadsHomeComponent],
  imports: [
    CommonModule,
    NxHeadlineModule,
    NxLinkModule,
    SdcSquadsHomeRoutingModule,
    SdcComplianceBarCardsModule,
    SdcCoveragesModule,
    SdcSquadSummaryModule,
    TranslateModule
  ]
})
export class SdcSquadsHomeModule {}
