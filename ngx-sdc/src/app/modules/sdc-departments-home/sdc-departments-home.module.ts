import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule } from 'src/app/shared/components/sdc-compliance-bar-cards/sdc-compliance-bar-cards.module';
import { SdcCoveragesModule } from 'src/app/shared/components/sdc-coverages/sdc-coverages.module';
import { SdcDepartmentSummaryModule } from 'src/app/shared/components/sdc-department-summary/sdc-department-summary.module';
import { SdcDepartmentsHomeRoutingModule } from './sdc-departments-home-routing.module';
import { SdcDepartmentsHomeComponent } from './sdc-departments-home.component';

@NgModule({
  declarations: [SdcDepartmentsHomeComponent],
  imports: [
    CommonModule,
    NxHeadlineModule,
    NxLinkModule,
    SdcDepartmentsHomeRoutingModule,
    SdcComplianceBarCardsModule,
    SdcCoveragesModule,
    SdcDepartmentSummaryModule,
    TranslateModule
  ]
})
export class SdcDepartmentsHomeModule {}
