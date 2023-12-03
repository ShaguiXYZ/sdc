import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console } from 'src/app/core/lib';
import { IMetricAnalysisModel, EvaluableValueType } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';
import { MetricsHistoryDataModel } from '../models';

@Injectable()
export class SdcMetricHistoryGraphsService {
  private metricData!: MetricsHistoryDataModel;
  private data$: Subject<MetricsHistoryDataModel>;

  constructor(private analysisService: AnalysisService) {
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
          componentAnalysis
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
}
