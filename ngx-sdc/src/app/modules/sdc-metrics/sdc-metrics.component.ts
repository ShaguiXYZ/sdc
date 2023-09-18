import { DatePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { UiAlertService } from 'src/app/core/components/alert';
import { MetricState, stateByCoverage } from 'src/app/core/lib';
import { IComponentModel, IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { SdcMetricsCardsComponent } from 'src/app/shared/components/sdc-metrics-cards';
import { MetricsChartConfig, MetricsDataModel } from './models';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, DatePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  @ViewChild('metricsCards') templateRef!: TemplateRef<any>;

  public metricsData?: MetricsDataModel;
  public metricChartConfig: MetricsChartConfig = { values: [] };
  public historicalChartConfig: MetricsChartConfig = { values: [] };

  private data$!: Subscription;
  private metricsCardsDialogRef?: NxModalRef<SdcMetricsCardsComponent>;

  constructor(
    private readonly alertService: UiAlertService,
    private readonly datePipe: DatePipe,
    private readonly dialogService: NxDialogService,
    private readonly sdcMetricsService: SdcMetricsService
  ) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;

      this.metricGraphConfig(this.metricsData.historicalAnalysis);
      this.applicationCoverageGraphConfig(this.metricsData.historical);
    });

    this.sdcMetricsService.loadInitData();
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

  openMetricsCards(): void {
    this.metricsCardsDialogRef = this.dialogService.open(this.templateRef, {
      appearance: 'expert',
      data: { component: this.metricsData?.compliance }
    });
  }

  closeMetricsCards() {
    this.metricsCardsDialogRef?.close();
  }

  private metricGraphConfig(analysis?: IMetricAnalysisModel[]) {
    this.metricChartConfig.values =
      analysis?.map(data => ({
        xAxis: this.datePipe.transform(data.analysisDate, 'dd/MM/yyyy') ?? '',
        data: data.analysisValues.metricValue,
        color: MetricState[stateByCoverage(data.coverage)].color,
        type: data.metric.valueType
      })) ?? [];
  }

  private applicationCoverageGraphConfig(historical?: IHistoricalCoverage<IComponentModel>) {
    const analysis = historical?.historical.page ?? [];

    this.historicalChartConfig.values =
      analysis?.map(data => ({
        xAxis: this.datePipe.transform(data.analysisDate, 'dd/MM/yyyy') ?? '',
        data: `${data.coverage}`,
        color: MetricState[stateByCoverage(data.coverage)].color,
        type: ValueType.NUMERIC
      })) ?? [];
  }
}
