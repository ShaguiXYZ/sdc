import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { AppSharedModule } from 'src/app/shared/shared.module';
import { SdcSummaryRoutingModule } from './sdc-summary-routing.module';
import { SdcSummaryComponent } from './sdc-summary.component';

@NgModule({
  declarations: [SdcSummaryComponent],
  imports: [AppSharedModule, CommonModule, SdcSummaryRoutingModule, TranslateModule]
})
export class SdcSummaryModule {}
