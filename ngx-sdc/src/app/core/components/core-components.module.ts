import { NgModule } from '@angular/core';
import { UiAlertModule } from './alert';
import { UiHeaderModule } from './header';
import { UiLoadingModule } from './loading';
import { UiNotificationModule } from './notification';

@NgModule({
  declarations: [],
  imports: [],
  exports: [UiAlertModule, UiHeaderModule, UiLoadingModule, UiNotificationModule]
})
export class UiCoreComponentsModule {}
