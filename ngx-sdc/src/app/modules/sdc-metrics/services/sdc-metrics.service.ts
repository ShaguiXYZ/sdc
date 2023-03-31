import { Injectable } from '@angular/core';
import { UiContextDataService } from 'src/app/core/services';
import { ContextDataInfo, MetricsContextData } from 'src/app/shared/constants/context-data';

@Injectable()
export class SdcMetricsService {
  public contextData!: MetricsContextData;

  constructor(private contextDataService: UiContextDataService) {
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.METRICS_DATA);
  }
}
