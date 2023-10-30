import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { UiAlertComponent } from './alert.component';

@NgModule({
  declarations: [UiAlertComponent],
  imports: [CommonModule, NxCopytextModule, NxHeadlineModule, NxButtonModule, NxModalModule],
  exports: [UiAlertComponent]
})
export class UiAlertModule {}
