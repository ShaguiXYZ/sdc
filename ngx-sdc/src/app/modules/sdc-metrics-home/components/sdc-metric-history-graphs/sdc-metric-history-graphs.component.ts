import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { SdcMetricInfoComponent, SdcNoDataComponent } from 'src/app/shared/components';
import { SdcTimeEvolutionChartComponent } from 'src/app/shared/components/sdc-charts';
import { MetricStates, DEFAULT_METRIC_STATE, MetricState, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig, ChartData } from 'src/app/shared/models';
import { MetricsHistoryDataModel } from './models';
import { SdcMetricHistoryGraphsService } from './services';
import { SdcValueTypeToNumberPipe } from 'src/app/shared/pipes';

@Component({
  selector: 'sdc-metric-history-graphs',
  templateUrl: './sdc-metric-history-graphs.component.html',
  styleUrls: ['./sdc-metric-history-graphs.component.scss'],
  providers: [SdcMetricHistoryGraphsService, SdcValueTypeToNumberPipe],
  standalone: true,
  imports: [SdcMetricInfoComponent, SdcNoDataComponent, SdcTimeEvolutionChartComponent, CommonModule, NxCopytextModule, TranslateModule]
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
    private readonly dateService: DateService,
    private readonly sdcMetricHistoryGraphsService: SdcMetricHistoryGraphsService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService,
    private readonly valueTypeToNumberPipe: SdcValueTypeToNumberPipe
  ) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricHistoryGraphsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;
      this.metricGraphConfig();
    });

    this.sdcMetricHistoryGraphsService.loadInitData(this.componentId, this.selectedAnalysis);
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public loadGraphData(analysis: IMetricAnalysisModel): void {
    this.sdcMetricHistoryGraphsService.analysisHistoryData(this.componentId, analysis);

    this.selectedAnalysisChange.emit(analysis);
  }

  private metricGraphConfig(): void {
    const analysis = this.metricsData?.historicalAnalysis;

    const chartData: Array<ChartData> = [
      {
        name: this.metricsData?.selectedAnalysis?.metric.name,
        values:
          analysis?.map(value => ({
            color: value.metric.validation ? MetricState[stateByCoverage(value.coverage)].color : MetricState[DEFAULT_METRIC_STATE].color,
            value:
              this.valueTypeToNumberPipe.transform(
                value.analysisValues.metricValue,
                this.metricsData?.selectedAnalysis?.metric.valueType
              ) ?? 0
          })) ?? []
      }
    ];

    if (analysis?.some(data => data.analysisValues.expectedValue)) {
      chartData.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[MetricStates.WITH_RISK].style}`)),
        values:
          analysis?.map(value => ({
            color: MetricState[MetricStates.WITH_RISK].color,
            value:
              this.valueTypeToNumberPipe.transform(
                value.analysisValues.expectedValue,
                this.metricsData?.selectedAnalysis?.metric.valueType
              ) ?? 0
          })) ?? []
      });
    }

    if (analysis?.some(data => data.analysisValues.goodValue)) {
      chartData.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[MetricStates.ACCEPTABLE].style}`)),
        values:
          analysis?.map(value => ({
            color: MetricState[MetricStates.ACCEPTABLE].color,
            value:
              this.valueTypeToNumberPipe.transform(value.analysisValues.goodValue, this.metricsData?.selectedAnalysis?.metric.valueType) ??
              0
          })) ?? []
      });
    }

    if (analysis?.some(data => data.analysisValues.perfectValue)) {
      chartData.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[MetricStates.PERFECT].style}`)),
        values:
          analysis?.map(value => ({
            color: MetricState[MetricStates.PERFECT].color,
            value:
              this.valueTypeToNumberPipe.transform(
                value.analysisValues.perfectValue,
                this.metricsData?.selectedAnalysis?.metric.valueType
              ) ?? 0
          })) ?? []
      });
    }

    this.metricChartConfig = {
      axis: {
        xAxis: analysis?.map(value => this.dateService.dateFormat(value.analysisDate))
      },
      data: chartData,
      options: { showVisualMap: true },
      type: this.metricsData?.selectedAnalysis?.metric.valueType
    };
  }
}
