import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UiAlertService } from 'src/app/core/components/alert';
import { MetricState, stateByCoverage } from 'src/app/core/lib';
import { IComponentModel, IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { MetricsChartConfig, MetricsDataModel } from './models';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, DatePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsDataModel;
  public metricChartConfig: MetricsChartConfig = { values: [] };
  public historicalChartConfig: MetricsChartConfig = { values: [] };

  private data$!: Subscription;

  constructor(private datePipe: DatePipe, private alertService: UiAlertService, private sdcMetricsService: SdcMetricsService) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;

      this.metricGraphConfig(this.metricsData.analysis);
      this.applicationCoverageGraphConfig(this.metricsData.historical);
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  loadGraphData(analysis: IMetricAnalysisModel): void {
    this.sdcMetricsService.analysisData(analysis);
  }

  onOpenPanel(): void {
    if (!this.metricsData?.historical) {
      this.sdcMetricsService.historicalComponentData();
    }
  }

  onRunProcess() {
    this.alertService.confirm(
      {
        title: 'Alerts.RunProcess.Title',
        text: 'Alerts.RunProcess.Description'
      },
      this.sdcMetricsService.analyze,
      { okText: 'Label.Yes', cancelText: 'Label.No' }
    );
  }

  private metricGraphConfig(analysis?: IMetricAnalysisModel[]) {
    this.metricChartConfig.values =
      analysis?.map(data => ({
        xAxis: this.datePipe.transform(data.analysisDate, 'dd/MM/yyyy') || '',
        data: data.analysisValues.metricValue,
        color: MetricState[stateByCoverage(data.coverage)].color,
        type: data.metric.valueType
      })) || [];
  }

  private applicationCoverageGraphConfig(historical?: IHistoricalCoverage<IComponentModel>) {
    const analysis = historical?.historical.page || [];

    this.historicalChartConfig.values =
      analysis?.map(data => ({
        xAxis: this.datePipe.transform(data.analysisDate, 'dd/MM/yyyy') || '',
        data: `${data.coverage}`,
        color: MetricState[stateByCoverage(data.coverage)].color,
        type: ValueType.NUMERIC
      })) || [];
  }
}
