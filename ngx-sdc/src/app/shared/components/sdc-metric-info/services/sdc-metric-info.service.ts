import { Injectable } from '@angular/core';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { IUriModel, UriType } from 'src/app/core/models/sdc';
import { UriService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';

@Injectable()
export class SdcMetricInfoService {
  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly uriService: UriService
  ) {}

  public componentUriByType(type: UriType): Promise<IUriModel> {
    const metricContextData = this.contextDataService.get<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA);

    return this.uriService.componentUriByType(metricContextData.component.id, type);
  }
}
