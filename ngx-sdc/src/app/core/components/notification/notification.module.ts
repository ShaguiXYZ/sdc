import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxMessageModule } from '@aposin/ng-aquila/message';
import { UiNotificationComponent } from './notification.component';

@NgModule({
  declarations: [UiNotificationComponent],
  imports: [CommonModule, NxButtonModule, NxMessageModule,NxIconModule],
  exports: [UiNotificationComponent]
})
export class UiNotificationModule {}
