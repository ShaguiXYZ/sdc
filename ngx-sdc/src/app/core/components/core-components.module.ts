import { NgModule } from '@angular/core';
import { AlertComponent } from './alert';
import { HeaderComponent } from './header';
import { LoadingComponent } from './loading';
import { NotificationComponent } from './notification';

@NgModule({
  declarations: [],
  imports: [AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent],
  exports: [AlertComponent, HeaderComponent, LoadingComponent, NotificationComponent]
})
export class CoreComponentsModule {}
