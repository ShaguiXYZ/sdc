import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule, SdcCoveragesModule, SdcDepartmentSummaryModule } from 'src/app/shared/components';
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
