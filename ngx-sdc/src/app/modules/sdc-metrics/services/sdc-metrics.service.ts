import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { UiContextDataService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { ComponentService } from 'src/app/core/services/sdc/component.services';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { MetricsDataModel } from '../models';

@Injectable()
export class SdcMetricsService {
  private metricData!: MetricsDataModel;
  private data$: Subject<MetricsDataModel>;

  constructor(
    private contextDataService: UiContextDataService,
    private analysisService: AnalysisService,
    private componentService: ComponentService
  ) {
    this.data$ = new Subject();
    this.metricData = { compliance: this.contextDataService.getContextData(ContextDataInfo.METRICS_DATA).compliance };

    this.loadData();
  }

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public analysisData(metric: number): void {
    this.analysisService.metricHistory(this.metricData.compliance.id, metric).then(data => {
      this.metricData = { ...this.metricData, analysis: data.page };
      this.data$.next(this.metricData);
    });
  }

  private loadData(): void {
    this.componentService.componentMetrics(this.metricData.compliance.id).then(metrics => {
      this.metricData = { ...this.metricData, metrics: metrics.page.filter(metric => metric.validation && metric.valueType) };
      this.data$.next(this.metricData);
    });
  }
}
