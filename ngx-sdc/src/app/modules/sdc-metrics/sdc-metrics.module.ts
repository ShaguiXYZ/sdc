import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import {
  SdcComplianceBarCardModule,
  SdcMetricInfoModule,
  SdcMetricsCardsModule,
  SdcTimeEvolutionChartModule
} from 'src/app/shared/components';
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
    NxTooltipModule,
    SdcComplianceBarCardModule,
    SdcMetricHistoryGraphsModule,
    SdcMetricsCardsModule,
    SdcMetricsRoutingModule,
    SdcMetricInfoModule,
    SdcTimeEvolutionChartModule,
    TranslateModule
  ]
})
export class SdcMetricsModule {}
