import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import {
  SdcComplianceBarCardModule,
  SdcMetricInfoModule,
  SdcMetricsCardsModule,
  SdcNoDataModule,
  SdcTimeEvolutionChartModule
} from 'src/app/shared/components';
import { SdcMetricsRoutingModule } from './sdc-metrics-routing.module';
import { SdcMetricsComponent } from './sdc-metrics.component';

@NgModule({
  declarations: [SdcMetricsComponent],
  imports: [
    CommonModule,
    NxAccordionModule,
    NxButtonModule,
    NxCopytextModule,
    NxIconModule,
    NxModalModule,
    NxTooltipModule,
    SdcComplianceBarCardModule,
    SdcMetricsCardsModule,
    SdcMetricsRoutingModule,
    SdcMetricInfoModule,
    SdcNoDataModule,
    SdcTimeEvolutionChartModule,
    TranslateModule
  ]
})
export class SdcMetricsModule {}
