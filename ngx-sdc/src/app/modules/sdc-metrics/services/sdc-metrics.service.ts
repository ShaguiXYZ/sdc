import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { MetricsContextData, MetricsDataModel } from '../models';
import { IComplianceModel } from 'src/app/shared/components';
import { logError } from 'src/app/core/lib';

@Injectable()
export class SdcMetricsService {
  private metricContextData!: MetricsContextData;
  private metricData!: MetricsDataModel;
  private data$: Subject<MetricsDataModel>;

  constructor(
    private contextDataService: UiContextDataService,
    private analysisService: AnalysisService,
    private componentService: ComponentService,
    private departmentService: DepartmentService,
    private squadService: SquadService
  ) {
    this.data$ = new Subject();

    this.metricContextData = this.contextDataService.get(ContextDataInfo.METRICS_DATA);
    this.metricData = { compliance: this.metricContextData.compliance };

    this.loadInitData();
  }

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public analysisData(analysis: IMetricAnalysisModel): void {
    this.analysisService
      .metricHistory(this.metricData.compliance.id, analysis.metric.id)
      .then(data => {
        this.metricData = { ...this.metricData, analysis: data.page, selectedAnalysis: analysis };

        this.contextDataService.set(ContextDataInfo.METRICS_DATA, { ...this.metricContextData, selected: analysis });
        this.data$.next(this.metricData);
      })
      .catch(logError);
  }

  public historicalComponentData(): void {
    this.componentService
      .historical(this.metricData.compliance.id)
      .then(historical => {
        this.metricData.historical = historical;
        this.data$.next(this.metricData);
      })
      .catch(logError);
  }

  public analyze = (): void => {
    this.analysisService
      .analize(this.metricData.compliance.id)
      .then(analysis => {
        if (analysis.page.length) {
          this.componentService
            .component(this.metricData.compliance.id)
            .then(data => {
              this.metricData.compliance = IComplianceModel.fromComponentModel(data);
              this.componentService.clearSquadCache(data.squad.id);
              this.departmentService.clearCache();
              this.squadService.clearCache();
            })
            .catch(logError);

          this.loadInitData();
          this.historicalComponentData();
        }
      })
      .catch(logError);
  };

  private loadInitData(): void {
    this.componentService
      .componentMetrics(this.metricData.compliance.id)
      .then(metrics => {
        const availableMetrics = metrics.page.filter(metric => metric.validation && metric.valueType);
        this.metricData = {
          ...this.metricData,
          metrics: availableMetrics
        };

        this.data$.next(this.metricData);

        this.analysisService
          .analysis(this.metricData.compliance.id, this.metricContextData.selected?.metric.id ?? availableMetrics[0].id)
          .then(data => this.analysisData(data))
          .catch(logError);
      })
      .catch(logError);
  }
}
