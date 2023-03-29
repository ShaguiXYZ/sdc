import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { UiAlertComponent } from './alert.component';

@NgModule({
  declarations: [UiAlertComponent],
  imports: [CommonModule, NxButtonModule],
  exports: [UiAlertComponent]
})
export class UiAlertModule {}
