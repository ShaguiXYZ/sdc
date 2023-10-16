import { TitleCasePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalRef } from '@aposin/ng-aquila/modal';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { UiAlertService } from 'src/app/core/components/alert';
import { AvailableMetricStates, DEFAULT_METRIC_STATE, MetricState, stateByCoverage } from 'src/app/core/lib';
import { IComponentModel, IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { UiDateService } from 'src/app/core/services';
import { SdcMetricsCardsComponent } from 'src/app/shared/components/sdc-metrics-cards';
import { ChartConfig, ChartData } from 'src/app/shared/models';
import { MetricsDataModel } from './models';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, TitleCasePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  @ViewChild('metricsCards') templateRef!: TemplateRef<any>;

  public metricsData?: MetricsDataModel;
  public metricChartConfig!: ChartConfig;
  public historicalChartConfig!: ChartConfig;

  private data$!: Subscription;
  private metricsCardsDialogRef?: NxModalRef<SdcMetricsCardsComponent>;

  constructor(
    private readonly alertService: UiAlertService,
    private readonly dateService: UiDateService,
    private readonly dialogService: NxDialogService,
    private readonly sdcMetricsService: SdcMetricsService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService
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

  onRunProcess(): void {
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

  closeMetricsCards(): void {
    this.metricsCardsDialogRef?.close();
  }

  private metricGraphConfig(analysis?: IMetricAnalysisModel[]): void {
    const data: Array<ChartData> = [
      {
        name: this.metricsData?.selectedAnalysis?.metric.name,
        values:
          analysis?.map(value => ({
            color: value.metric.validation ? MetricState[stateByCoverage(value.coverage)].color : MetricState[DEFAULT_METRIC_STATE].color,
            value: value.analysisValues.metricValue
          })) ?? []
      }
    ];

    if (analysis?.some(data => data.analysisValues.expectedValue)) {
      data.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(
          this.translateService.instant(`Component.State.${MetricState[AvailableMetricStates.WITH_RISK].style}`)
        ),
        values:
          analysis?.map(value => ({
            color: MetricState[AvailableMetricStates.WITH_RISK].color,
            value: value.analysisValues.expectedValue ?? ''
          })) ?? []
      });
    }

    if (analysis?.some(data => data.analysisValues.goodValue)) {
      data.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(
          this.translateService.instant(`Component.State.${MetricState[AvailableMetricStates.ACCEPTABLE].style}`)
        ),
        values:
          analysis?.map(value => ({
            color: MetricState[AvailableMetricStates.ACCEPTABLE].color,
            value: value.analysisValues.goodValue ?? ''
          })) ?? []
      });
    }

    if (analysis?.some(data => data.analysisValues.perfectValue)) {
      data.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(
          this.translateService.instant(`Component.State.${MetricState[AvailableMetricStates.PERFECT].style}`)
        ),
        values:
          analysis?.map(value => ({
            color: MetricState[AvailableMetricStates.PERFECT].color,
            value: value.analysisValues.perfectValue ?? ''
          })) ?? []
      });
    }

    this.metricChartConfig = {
      axis: {
        xAxis: analysis?.map(value => this.dateService.dateFormat(value.analysisDate))
      },
      data,
      options: { showVisualMap: true },
      type: analysis?.find(value => value.metric.valueType)?.metric.valueType
    };
  }

  private applicationCoverageGraphConfig(historical?: IHistoricalCoverage<IComponentModel>): void {
    const analysis = historical?.historical.page ?? [];

    this.historicalChartConfig = {
      axis: {
        xAxis: analysis?.map(data => this.dateService.dateFormat(data.analysisDate))
      },
      data: [
        {
          values:
            analysis?.map(value => ({
              value: `${value.coverage}`,
              color: MetricState[stateByCoverage(value.coverage)].color
            })) ?? []
        }
      ],
      options: { showVisualMap: true },
      type: ValueType.NUMERIC
    };
  }
}
