import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { NxPaginationModule } from '@aposin/ng-aquila/pagination';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardsModule } from 'src/app/shared/components';
import { SdcApplicationsRoutingModule } from './sdc-applications-routing.module';
import { SdcApplicationsComponent } from './sdc-applications.component';

@NgModule({
  declarations: [SdcApplicationsComponent],
  imports: [
    CommonModule,
    FormsModule,
    NxDropdownModule,
    NxFormfieldModule,
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
