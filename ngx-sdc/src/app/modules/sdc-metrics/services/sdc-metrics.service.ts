import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console, emptyFn } from 'src/app/core/lib';
import { AnalysisType, IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { UiContextDataService, UiDateService } from 'src/app/core/services';
import { AnalysisService, ComponentService, DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { IComplianceModel } from 'src/app/shared/components';
import { ContextDataInfo, LANGUAGE_DISTIBUTION_METRIC } from 'src/app/shared/constants';
import { MetricsContextData, MetricsDataModel } from '../models';

@Injectable()
export class SdcMetricsService {
  private metricContextData!: MetricsContextData;
  private metricData!: MetricsDataModel;
  private data$: Subject<MetricsDataModel>;
  private tabActions: { fn: () => void }[] = [];

  constructor(
    private readonly contextDataService: UiContextDataService,
    private readonly analysisService: AnalysisService,
    private readonly componentService: ComponentService,
    private readonly dateService: UiDateService,
    private readonly departmentService: DepartmentService,
    private readonly squadService: SquadService
  ) {
    this.data$ = new Subject();
    this.metricContextData = this.contextDataService.get(ContextDataInfo.METRICS_DATA);
    this.metricData = {
      compliance: this.metricContextData.compliance,
      selectedAnalysis: this.metricContextData.selected,
      selectedTabIndex: this.metricContextData.selectedTabIndex
    };
    this.tabActions = [{ fn: emptyFn }, { fn: this.languageDistribution }];
    this.tabSelected = this.metricContextData.selectedTabIndex ?? 0;
  }

  public set metricAnalysisSeleted(analysis: IMetricAnalysisModel) {
    this.metricContextData.selected = analysis;
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, this.metricContextData);
  }

  public set tabSelected(index: number) {
    this.tabActions[index]?.fn();

    this.metricContextData.selectedTabIndex = index;
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

  private languageDistribution = (): void => {
    this.analysisService.componentAnalysis(this.metricData.compliance.id).then(data => {
      const metricAnalysis = data.page.find(
        analysis =>
          analysis.name.toLowerCase() === LANGUAGE_DISTIBUTION_METRIC.name.toLowerCase() &&
          LANGUAGE_DISTIBUTION_METRIC.type === analysis.metric.type
      );

      if (metricAnalysis) {
        this.analysisService.metricHistory(this.metricData.compliance.id, metricAnalysis.metric.id).then(history => {
          this.metricData.languageDistribution = {
            graph: history.page.map(analysis => ({
              axis: this.dateService.dateFormat(analysis.analysisDate),
              data: analysis.analysisValues.metricValue
            })),
            legendPosition: 'right',
            type: ValueType.NUMERIC
          };

          this.data$.next(this.metricData);
        });
      }
    });
  };
}
