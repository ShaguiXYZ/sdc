import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { NxIconModule } from '@aposin/ng-aquila/icon';
import { NxModalModule } from '@aposin/ng-aquila/modal';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { SdcComplianceBarCardModule } from 'src/app/shared/components/sdc-compliance-bar-card/sdc-compliance-bar-card.module';
import { SdcMetricInfoModule } from 'src/app/shared/components/sdc-metric-info/sdc-metric-info.module';
import { SdcMetricsCardsModule } from 'src/app/shared/components/sdc-metrics-cards/sdc-metrics-cards.module';
import { SdcTimeEvolutionChartModule } from 'src/app/shared/components/sdc-time-evolution-chart/sdc-time-evolution-chart.module';
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
    SdcTimeEvolutionChartModule,
    TranslateModule
  ]
})
export class SdcMetricsModule {}
