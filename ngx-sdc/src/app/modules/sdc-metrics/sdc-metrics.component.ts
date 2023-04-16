import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MetricState, stateByCoverage } from 'src/app/core/lib';
import { IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { VisualPiece } from 'src/app/shared/components/sdc-time-evolution-chart';
import { MetricsDataModel } from './models';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, DatePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsDataModel;
  public values: { xAxis: string; data: string; type?: ValueType }[] = [];
  public pieces: VisualPiece[] = [];

  private data$!: Subscription;

  constructor(private datePipe: DatePipe, private sdcMetricsService: SdcMetricsService) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;

      this.pieces =
        this.metricsData.analysis?.map((analysis, index) => ({
          gte: index,
          lt: index + 1,
          color: MetricState[stateByCoverage(analysis.coverage)].color
        })) || [];

      this.values =
        this.metricsData.analysis?.map(analysis => ({
          xAxis: this.datePipe.transform(analysis.analysisDate, 'dd/MM/yyyy') || '',
          data: analysis.analysisValues.metricValue,
          type: analysis.metric.valueType
        })) || [];
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  loadGraphData(analysis: IMetricAnalysisModel): void {
    this.sdcMetricsService.analysisData(analysis);
  }

  onOpenPanel(): void {
    console.log('Panel opened');
  }
}
