import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { AnalysisService, ComponentService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { MetricsContextData, MetricsDataModel } from '../models';

@Injectable()
export class SdcMetricsService {
  private metricContextData!: MetricsContextData;
  private metricData!: MetricsDataModel;
  private data$: Subject<MetricsDataModel>;

  constructor(
    private contextDataService: UiContextDataService,
    private analysisService: AnalysisService,
    private componentService: ComponentService
  ) {
    this.data$ = new Subject();

    this.metricContextData = this.contextDataService.getContextData(ContextDataInfo.METRICS_DATA);
    this.metricData = { compliance: this.metricContextData.compliance };

    this.loadInitData();
  }

  public retrieveCoverage = async (componentId: number, metric: IMetricModel): Promise<any> => {
    await this.analysisService.analysis(componentId, metric.id).then(value => value.coverage);
  };

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public analysisData(analysis: IMetricAnalysisModel): void {
    this.analysisService.metricHistory(this.metricData.compliance.id, analysis.metric.id).then(data => {
      this.metricData = { ...this.metricData, analysis: data.page, selectedAnalysis: analysis };

      this.contextDataService.setContextData(ContextDataInfo.METRICS_DATA, { ...this.metricContextData, selected: analysis });
      this.data$.next(this.metricData);
    });
  }

  public historicalComponentData(): void {
    this.componentService.historical(this.metricData.compliance.id).then(historical => {
      this.metricData.historical = historical;
      this.data$.next(this.metricData);
    });
  }

  private loadInitData(): void {
    this.componentService.componentMetrics(this.metricData.compliance.id).then(metrics => {
      const availableMetrics = metrics.page.filter(metric => metric.validation && metric.valueType);
      this.metricData = {
        ...this.metricData,
        metrics: availableMetrics
      };

      this.data$.next(this.metricData);

      this.analysisService
        .analysis(this.metricData.compliance.id, this.metricContextData.selected?.metric.id ?? availableMetrics[0].id)
        .then(data => this.analysisData(data));
    });
  }
}
