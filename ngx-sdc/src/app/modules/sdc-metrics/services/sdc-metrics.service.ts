import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console } from 'src/app/core/lib';
import { UiContextDataService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { IComplianceModel } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { MetricsContextData, MetricsDataModel } from '../models';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';

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
    this.metricData = { compliance: this.metricContextData.compliance, selectedAnalysis: this.metricContextData.selected };
  }

  public set metricAnalysisSeleted(analysis: IMetricAnalysisModel) {
    this.metricContextData.selected = analysis;
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, this.metricContextData);
  }

  public loadInitData(): void {
    this.data$.next(this.metricData);
  }

  public onDataChange(): Observable<MetricsDataModel> {
    return this.data$.asObservable();
  }

  public historicalComponentData(): void {
    this.componentService
      .historical(this.metricData.compliance.id)
      .then(historical => {
        this.metricData.historical = historical;
        this.data$.next(this.metricData);
      })
      .catch(_console.error);
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
              this.analysisService.clearAnalysisCache(this.metricData.compliance.id);
              this.componentService.clearSquadCache(data.squad.id);
              this.departmentService.clearCache();
              this.squadService.clearCache();

              this.historicalComponentData();
            })
            .catch(_console.error);
        }
      })
      .catch(_console.error);
  };
}
