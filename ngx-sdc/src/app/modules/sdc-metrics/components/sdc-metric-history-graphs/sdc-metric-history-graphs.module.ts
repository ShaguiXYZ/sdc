import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { SdcMetricInfoModule, SdcNoDataModule, SdcTimeEvolutionChartModule } from 'src/app/shared/components';
import { SdcMetricHistoryGraphsComponent } from './sdc-metric-history-graphs.component';

@NgModule({
  declarations: [SdcMetricHistoryGraphsComponent],
  imports: [CommonModule, SdcMetricInfoModule, SdcNoDataModule, SdcTimeEvolutionChartModule, TranslateModule],
  exports: [SdcMetricHistoryGraphsComponent]
})
export class SdcMetricHistoryGraphsModule {}
