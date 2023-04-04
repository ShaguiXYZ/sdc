import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxPaginationModule } from '@aposin/ng-aquila/pagination';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule } from 'src/app/shared/components/sdc-compliance-bar-cards/sdc-compliance-bar-cards.module';
import { SdcApplicationsRoutingModule } from './sdc-applications-routing.module';
import { SdcApplicationsComponent } from './sdc-applications.component';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';

@NgModule({
  declarations: [SdcApplicationsComponent],
  imports: [
    CommonModule,
    FormsModule,
    NxDropdownModule,
    NxFormfieldModule,
    NxGridModule,
    NxIconModule,
    NxInputModule,
    NxPaginationModule,
    NxRadioToggleModule,
    ReactiveFormsModule,
    SdcApplicationsRoutingModule,
    SdcComplianceBarCardsModule,
    TranslateModule
  ]
})
export class SdcApplicationsModule {}
