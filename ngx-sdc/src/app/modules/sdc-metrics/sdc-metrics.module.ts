import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SdcMetricsComponent } from './sdc-metrics.component';
import { SdcComplianceBarCardModule } from 'src/app/shared/components/sdc-compliance-bar-card/sdc-compliance-bar-card.module';
import { SdcMetricsRoutingModule } from './sdc-metrics-routing.module';

@NgModule({
  declarations: [SdcMetricsComponent],
  imports: [CommonModule, SdcComplianceBarCardModule, SdcMetricsRoutingModule]
})
export class SdcMetricsModule {}
