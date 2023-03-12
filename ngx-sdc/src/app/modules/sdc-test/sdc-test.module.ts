import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { TranslateModule } from '@ngx-translate/core';
import { AppSharedModule } from 'src/app/shared/shared.module';
import { SdcTestPageComponent, SdcTestPageRoutingModule, SdcTestService } from '.';

@NgModule({
  declarations: [SdcTestPageComponent],
  imports: [
    AppSharedModule,
    CommonModule,
    FormsModule,
    NxDropdownModule,
    NxFormfieldModule,
    NxGridModule,
    NxIconModule,
    ReactiveFormsModule,
    SdcTestPageRoutingModule,
    TranslateModule
  ],
  providers: [SdcTestService]
})
export class SdcTestPageModule {}
