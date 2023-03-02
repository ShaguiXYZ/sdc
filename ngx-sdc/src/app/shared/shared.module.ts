import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxMessageModule } from '@aposin/ng-aquila/message';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { TranslateModule } from '@ngx-translate/core';
import { UiAlertComponent, UiLoadingComponent, UiNotificationComponent } from './components';
import { UiStateComponent } from './components/state/state.component';

@NgModule({
  declarations: [UiAlertComponent, UiLoadingComponent, UiNotificationComponent, UiStateComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    NxButtonModule,
    NxCopytextModule,
    NxGridModule,
    NxHeadlineModule,
    NxIconModule,
    NxMessageModule,
    NxModalModule.forRoot(),
    NxSpinnerModule
  ],
  exports: [UiStateComponent],
  providers: []
})
export class AppSharedModule {}
