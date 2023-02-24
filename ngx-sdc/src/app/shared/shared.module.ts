import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxErrorModule } from '@aposin/ng-aquila/base';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxCheckboxModule } from '@aposin/ng-aquila/checkbox';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    NxAccordionModule,
    NxButtonModule,
    NxCardModule,
    NxCheckboxModule,
    NxCopytextModule,
    NxErrorModule,
    NxFormfieldModule,
    NxGridModule,
    NxIconModule,
    NxInputModule,
    NxLinkModule
  ],
  exports: [],
  providers: []
})
export class AppSharedModule {}
