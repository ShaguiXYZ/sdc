import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule } from 'src/app/shared/components/sdc-compliance-bar-cards/sdc-compliance-bar-cards.module';
import { SdcCoveragesModule } from 'src/app/shared/components/sdc-coverages/sdc-coverages.module';
import { SdcSquadSummaryModule } from 'src/app/shared/components/sdc-squad-summary/sdc-squad-summary.module';
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
