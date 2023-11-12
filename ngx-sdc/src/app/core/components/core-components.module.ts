import { NgModule } from '@angular/core';
import { UiAlertComponent } from './alert';
import { UiHeaderComponent } from './header';
import { UiLoadingComponent } from './loading';
import { UiNotificationComponent } from './notification';

@NgModule({
  declarations: [],
  imports: [UiAlertComponent, UiHeaderComponent, UiLoadingComponent, UiNotificationComponent],
  exports: [UiAlertComponent, UiHeaderComponent, UiLoadingComponent, UiNotificationComponent]
})
export class UiCoreComponentsModule {}
