import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { UiContextDataService } from 'src/app/core/services';
import { ComponentService } from 'src/app/core/services/sdc/component.services';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { MetricsContextData } from '../models';
import { MetricsDataModel } from '../models/sdc-metrics-data.model';

@Injectable()
export class SdcMetricsService {
  public contextData: MetricsContextData;

  private data$: Subject<MetricsDataModel>;

  constructor(private contextDataService: UiContextDataService, private componentService: ComponentService) {
    this.data$ = new Subject();
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.METRICS_DATA);

    this.loadData();
  }

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public context(): MetricsContextData {
    return this.contextData;
  }

  private loadData() {
    this.componentService.componentMetrics(this.contextData.compliance.id).then(metrics => {
      this.data$.next({ metrics: metrics.page.filter(metric => metric.validation && metric.valueType) });
    });
  }
}
