import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule } from 'src/app/shared/components/sdc-compliance-bar-cards/sdc-compliance-bar-cards.module';
import { SdcSquadSummaryModule } from 'src/app/shared/components/sdc-squad-summary/sdc-squad-summary.module';
import { SdcSquadsCoverageModule } from 'src/app/shared/components/sdc-squads-coverage/sdc-squads-coverage.module';
import { SdcSummaryRoutingModule } from './sdc-summary-routing.module';
import { SdcSummaryComponent } from './sdc-summary.component';

@NgModule({
  declarations: [SdcSummaryComponent],
  imports: [
    CommonModule,
    SdcSummaryRoutingModule,
    SdcComplianceBarCardsModule,
    SdcSquadsCoverageModule,
    SdcSquadSummaryModule,
    TranslateModule
  ]
})
export class SdcSummaryModule {}
