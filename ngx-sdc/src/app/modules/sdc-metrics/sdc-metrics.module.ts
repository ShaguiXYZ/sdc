import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import {
  SdcComplianceBarCardModule,
  SdcMetricInfoModule,
  SdcMetricsCardsModule,
  SdcNoDataModule,
  SdcTimeEvolutionMultichartModule
} from 'src/app/shared/components';
import { SdcTimeEvolutionChartModule } from 'src/app/shared/components/sdc-charts';
import { SdcMetricHistoryGraphsModule } from './components';
import { SdcMetricsRoutingModule } from './sdc-metrics-routing.module';
import { SdcMetricsComponent } from './sdc-metrics.component';

@NgModule({
  declarations: [SdcMetricsComponent],
  imports: [
    CommonModule,
    NxAccordionModule,
    NxButtonModule,
    NxModalModule,
    NxTabsModule,
    NxTooltipModule,
    SdcComplianceBarCardModule,
    SdcMetricHistoryGraphsModule,
    SdcMetricsCardsModule,
    SdcMetricsRoutingModule,
    SdcMetricInfoModule,
    SdcNoDataModule,
    SdcTimeEvolutionChartModule,
    SdcTimeEvolutionMultichartModule,
    TranslateModule
  ]
})
export class SdcMetricsModule {}
