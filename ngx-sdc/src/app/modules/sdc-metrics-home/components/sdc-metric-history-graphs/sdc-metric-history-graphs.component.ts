import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IAnalysisValuesModel, IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { SdcMetricInfoComponent, SdcNoDataComponent } from 'src/app/shared/components';
import { SdcTimeEvolutionChartComponent } from 'src/app/shared/components/sdc-charts';
import { MetricStates, DEFAULT_METRIC_STATE, MetricState, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig, ChartData, ChartValue } from 'src/app/shared/models';
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
  public metricChartConfig!: ChartConfig;
  public metricsData?: MetricsHistoryDataModel;

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
      this.metricChartConfig = {
        ...this.metricGraphConfig(),
        data: this.chartData(this.metricsData?.historicalAnalysis ?? [], this.metricsData.showFactorCharts)
      };
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

  private metricGraphConfig(): ChartConfig {
    const analysis = this.metricsData?.historicalAnalysis;

    return {
      axis: {
        xAxis: analysis?.map(value => this.dateService.dateFormat(value.analysisDate))
      },
      data: [],
      options: { showVisualMap: true },
      type: this.metricsData?.selectedAnalysis?.metric.valueType
    };
  }

  private chartData(analysis: IMetricAnalysisModel[], showFactorChart?: Record<keyof IAnalysisValuesModel, boolean>): Array<ChartData> {
    const data: Array<ChartData> = [
      {
        name: this.metricsData?.selectedAnalysis?.metric.name,
        values: analysis?.map(value => this.getChartDataValue(value)) ?? []
      }
    ];

    showFactorChart?.['expectedValue'] && this.addFactorChartData(data, analysis, MetricStates.WITH_RISK, 'expectedValue');
    showFactorChart?.['goodValue'] && this.addFactorChartData(data, analysis, MetricStates.ACCEPTABLE, 'goodValue');
    showFactorChart?.['perfectValue'] && this.addFactorChartData(data, analysis, MetricStates.PERFECT, 'perfectValue');

    return data;
  }

  private getChartDataValue(value: IMetricAnalysisModel): ChartValue {
    return {
      color: value.metric.validation ? MetricState[stateByCoverage(value.coverage)].color : MetricState[DEFAULT_METRIC_STATE].color,
      value:
        this.valueTypeToNumberPipe.transform(value.analysisValues.metricValue, this.metricsData?.selectedAnalysis?.metric.valueType) ?? 0
    };
  }

  private addFactorChartData(
    data: Array<ChartData>,
    analysis: IMetricAnalysisModel[],
    state: MetricStates,
    valueKey: keyof IMetricAnalysisModel['analysisValues']
  ): void {
    if (analysis?.some(data => data.analysisValues[valueKey])) {
      data.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[state].style}`)),
        values: analysis?.map(value => this.getFactorChartDataValue(value, state, valueKey)) ?? []
      });
    }
  }

  private getFactorChartDataValue(
    value: IMetricAnalysisModel,
    state: MetricStates,
    valueKey: keyof IMetricAnalysisModel['analysisValues']
  ): ChartValue {
    return {
      color: MetricState[state].color,
      value: this.valueTypeToNumberPipe.transform(value.analysisValues[valueKey], this.metricsData?.selectedAnalysis?.metric.valueType) ?? 0
    };
  }
}
