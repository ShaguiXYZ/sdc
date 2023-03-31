import { NgModule } from '@angular/core';
import { UiAlertModule } from './alert/alert.module';
import { UiHeaderModule } from './header/header.module';
import { UiLoadingModule } from './loading/loading.module';
import { UiNotificationModule } from './notification/notification.module';

@NgModule({
  declarations: [],
  imports: [],
  exports: [
    UiAlertModule,
    UiHeaderModule,
    UiLoadingModule,
    UiNotificationModule
  ]
})
export class UiCoreComponentsModule {}
