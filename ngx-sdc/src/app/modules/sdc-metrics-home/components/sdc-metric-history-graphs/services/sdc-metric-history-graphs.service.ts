import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console } from 'src/app/core/lib';
import { AnalysisFactor, EvaluableValueType, IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';
import { MetricsHistoryDataModel } from '../models';
import { ContextDataService } from 'src/app/core/services';
import { SdcMetricsContextData } from 'src/app/shared/models';
import { ContextDataInfo } from 'src/app/shared/constants';

@Injectable()
export class SdcMetricHistoryGraphsService {
  private metricData!: MetricsHistoryDataModel;
  private data$: Subject<MetricsHistoryDataModel>;

  constructor(
    private readonly analysisService: AnalysisService,
    private readonly contextDataService: ContextDataService
  ) {
    this.data$ = new Subject();
  }

  public onDataChange(): Observable<MetricsHistoryDataModel> {
    return this.data$.asObservable();
  }

  public loadInitData(componentId: number, selectedAnalysis?: IMetricAnalysisModel): void {
    this.analysisService
      .componentAnalysis(componentId)
      .then(data => {
        const componentAnalysis = data.page.filter(
          an => an.metric.valueType && EvaluableValueType.isEvaluableValueType(an.metric.valueType)
        );

        this.metricData = {
          ...this.metricData,
          componentAnalysis,
          showFactorCharts: this.contextDataService.get<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA)?.showFactorCharts
        };

        if (componentAnalysis.length) {
          this.metricData.selectedAnalysis = selectedAnalysis ?? componentAnalysis[0];
          this.analysisHistoryData(componentId, this.metricData.selectedAnalysis);
        } else {
          this.data$.next(this.metricData);
        }
      })
      .catch(_console.error);
  }

  public analysisHistoryData(componentId: number, analysis: IMetricAnalysisModel): void {
    this.analysisService
      .metricHistory(componentId, analysis.metric.id)
      .then(data => {
        this.metricData = { ...this.metricData, historicalAnalysis: data.page, selectedAnalysis: analysis };
        this.data$.next(this.metricData);
      })
      .catch(_console.error);
  }

  /**
   * This method is used to toggle the factor chart
   *
   * @param factor this is the factor that is being toggled
   */
  public toggleFactorChart(factor: AnalysisFactor): void {
    this.metricData = {
      ...this.metricData,
      showFactorCharts: {
        ...(this.metricData?.showFactorCharts ?? {}),
        [factor]: !this.metricData?.showFactorCharts?.[factor]
      } as Record<AnalysisFactor, boolean>
    };

    this.contextDataService.patch<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA, {
      showFactorCharts: this.metricData.showFactorCharts
    });

    this.data$.next(this.metricData);
  }
}
