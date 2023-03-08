import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxGridModule } from '@aposin/ng-aquila/grid';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxMessageModule } from '@aposin/ng-aquila/message';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { TranslateModule } from '@ngx-translate/core';
import { UiAlertComponent, UiLoadingComponent, UiNotificationComponent } from './components';
import { UiComplianceBarCardComponent } from './components/compliance-bar-card/compliance-bar-card.component';

@NgModule({
  declarations: [UiAlertComponent, UiComplianceBarCardComponent, UiLoadingComponent, UiNotificationComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    NxBadgeModule,
    NxButtonModule,
    NxCardModule,
    NxCopytextModule,
    NxGridModule,
    NxHeadlineModule,
    NxIconModule,
    NxMessageModule,
    NxModalModule.forRoot(),
    NxProgressbarModule,
    NxSpinnerModule
  ],
  exports: [UiComplianceBarCardComponent],
  providers: []
})
export class AppSharedModule {}
