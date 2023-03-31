import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { UiAlertComponent } from './alert.component';

@NgModule({
  declarations: [UiAlertComponent],
  imports: [CommonModule, NxButtonModule, NxModalModule],
  exports: [UiAlertComponent]
})
export class UiAlertModule {}
