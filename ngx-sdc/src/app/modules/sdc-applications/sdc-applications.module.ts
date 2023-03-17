import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { TranslateModule } from '@ngx-translate/core';
import { AppSharedModule } from 'src/app/shared/shared.module';
import { SdcApplicationsRoutingModule } from './sdc-applications-routing.module';
import { SdcApplicationsComponent } from './sdc-applications.component';

@NgModule({
  declarations: [SdcApplicationsComponent],
  imports: [
    AppSharedModule,
    CommonModule,
    FormsModule,
    NxDropdownModule,
    NxFormfieldModule,
    NxGridModule,
    NxIconModule,
    ReactiveFormsModule,
    SdcApplicationsRoutingModule,
    TranslateModule
  ]
})
export class SdcApplicationsModule {}
