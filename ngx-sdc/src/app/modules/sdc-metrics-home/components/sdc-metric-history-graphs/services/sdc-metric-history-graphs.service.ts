import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AnalysisFactor, EvaluableValueType, IMetricAnalysisModel, IPageable } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { AnalysisService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';
import { MetricsHistoryDataModel } from '../models';

@Injectable()
export class SdcMetricHistoryGraphsService {
  private metricData!: MetricsHistoryDataModel;
  private data$: Subject<MetricsHistoryDataModel> = new Subject();

  constructor(
    private readonly analysisService: AnalysisService,
    private readonly contextDataService: ContextDataService
  ) {}

  public onDataChange(): Observable<MetricsHistoryDataModel> {
    return this.data$.asObservable();
  }

  public async loadInitData(componentId: number, selectedAnalysis?: IMetricAnalysisModel): Promise<MetricsHistoryDataModel> {
    const data: IPageable<IMetricAnalysisModel> = await this.analysisService.componentAnalysis(componentId);

    const componentAnalysis = data.page.filter(an => an.metric.valueType && EvaluableValueType.isEvaluableValueType(an.metric.valueType));

    this.metricData = {
      ...this.metricData,
      componentAnalysis,
      showFactorCharts: this.contextDataService.get<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA)?.showFactorCharts
    };

    if (componentAnalysis.length) {
      this.metricData.selectedAnalysis = selectedAnalysis ?? componentAnalysis[0];
      await this.analysisHistoryData(componentId, this.metricData.selectedAnalysis);
    }

    return this.metricData;
  }

  public async analysisHistoryData(componentId: number, analysis: IMetricAnalysisModel): Promise<void> {
    const data: IPageable<IMetricAnalysisModel> = await this.analysisService.metricHistory(componentId, analysis.metric.id);

    this.metricData = { ...this.metricData, historicalAnalysis: data.page, selectedAnalysis: analysis };
    this.data$.next(this.metricData);
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
