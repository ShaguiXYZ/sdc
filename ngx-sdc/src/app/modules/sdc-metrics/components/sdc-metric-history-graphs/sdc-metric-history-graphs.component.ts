import { TitleCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { UiDateService } from 'src/app/core/services';
import { ChartConfig, ChartData } from 'src/app/shared/components/sdc-charts';
import { AvailableMetricStates, DEFAULT_METRIC_STATE, MetricState, stateByCoverage } from 'src/app/shared/lib';
import { MetricsHistoryDataModel } from './models';
import { SdcMetricHistoryGraphsService } from './services';

@Component({
  selector: 'sdc-metric-history-graphs',
  templateUrl: './sdc-metric-history-graphs.component.html',
  styleUrls: ['./sdc-metric-history-graphs.component.scss'],
  providers: [SdcMetricHistoryGraphsService]
})
export class SdcMetricHistoryGraphsComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsHistoryDataModel;
  public metricChartConfig!: ChartConfig;

  @Input()
  public componentId!: number;

  @Input()
  public selectedAnalysis?: IMetricAnalysisModel;

  @Output()
  public selectedAnalysisChange: EventEmitter<IMetricAnalysisModel> = new EventEmitter();

  private data$!: Subscription;

  constructor(
    private readonly dateService: UiDateService,
    private readonly sdcMetricHistoryGraphsService: SdcMetricHistoryGraphsService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricHistoryGraphsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;
      this.metricGraphConfig(this.metricsData.historicalAnalysis);
    });

    this.sdcMetricHistoryGraphsService.loadInitData(this.componentId, this.selectedAnalysis);
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  loadGraphData(analysis: IMetricAnalysisModel): void {
    this.sdcMetricHistoryGraphsService.analysisHistoryData(this.componentId, analysis);

    this.selectedAnalysisChange.emit(analysis);
  }

  private metricGraphConfig(analysis?: IMetricAnalysisModel[]): void {
    const chartData: Array<ChartData> = [
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
      chartData.push({
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
      chartData.push({
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
      chartData.push({
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
      data: chartData,
      options: { showVisualMap: true },
      type: analysis?.find(value => value.metric.valueType)?.metric.valueType
    };
  }
}
