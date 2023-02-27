import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { AppSharedModule } from 'src/app/shared/shared.module';
import { SdcHomePageRoutingModule } from './sdc-home-routing.module';
import { SdcHomePageComponent } from './sdc-home.component';

@NgModule({
  declarations: [SdcHomePageComponent],
  imports: [AppSharedModule, CommonModule, SdcHomePageRoutingModule, TranslateModule]
})
export class SdcHomePageModule {}
