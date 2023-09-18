import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcMetricInfoModule } from '../sdc-metric-info/sdc-metric-info.module';
import { SdcNoDataModule } from '../sdc-no-data/sdc-no-data.module';
import { SdcMetricsCardsComponent } from './sdc-metrics-cards.component';

@NgModule({
  declarations: [SdcMetricsCardsComponent],
  imports: [CommonModule, SdcMetricInfoModule, SdcNoDataModule, TranslateModule],
  exports: [SdcMetricsCardsComponent]
})
export class SdcMetricsCardsModule {}
