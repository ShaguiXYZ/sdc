import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { AppSharedModule } from 'src/app/shared/shared.module';
import { SdcHomePageComponent } from './sdc-home.component';

@NgModule({
  declarations: [SdcHomePageComponent],
  imports: [CommonModule, TranslateModule, AppSharedModule]
})
export class SdcHomePageModule {}
