import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NxCheckboxModule } from '@aposin/ng-aquila/checkbox';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AnalysisFactor, IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { DateService } from 'src/app/core/services';
import { SdcMetricInfoComponent } from 'src/app/shared/components';
import { ChartConfig, ChartData, ChartValue, SdcTimeEvolutionChartComponent } from 'src/app/shared/components/sdc-charts';
import { DEFAULT_METRIC_STATE, MetricState, MetricStates, stateByCoverage } from 'src/app/shared/lib';
import { SdcValueTypeToNumberPipe } from 'src/app/shared/pipes';
import { MetricsHistoryDataModel } from './models';
import { SdcMetricHistoryGraphsService } from './services';

@Component({
  selector: 'sdc-metric-history-graphs',
  templateUrl: './sdc-metric-history-graphs.component.html',
  styleUrls: ['./sdc-metric-history-graphs.component.scss'],
  providers: [SdcMetricHistoryGraphsService, SdcValueTypeToNumberPipe],
  standalone: true,
  imports: [CommonModule, NxCopytextModule, NxCheckboxModule, SdcMetricInfoComponent, SdcTimeEvolutionChartComponent, TranslateModule]
})
export class SdcMetricHistoryGraphsComponent implements OnInit, OnDestroy {
  public availableFactorCharts: { [key in AnalysisFactor]: boolean } = {
    expectedValue: false,
    goodValue: false,
    perfectValue: false
  };
  public availableFactorChartsKeys: AnalysisFactor[] = Object.keys(this.availableFactorCharts) as AnalysisFactor[];
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
    private readonly metricHistoryGraphsService: SdcMetricHistoryGraphsService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService,
    private readonly valueTypeToNumberPipe: SdcValueTypeToNumberPipe
  ) {}

  ngOnInit(): void {
    this.data$ = this.metricHistoryGraphsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;
      this.metricChartConfig = {
        ...this.metricGraphConfig(),
        data: this.chartData(this.metricsData?.historicalAnalysis ?? [], this.metricsData.showFactorCharts)
      };
    });

    this.metricHistoryGraphsService.loadInitData(this.componentId, this.selectedAnalysis);
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public loadGraphData(analysis: IMetricAnalysisModel): void {
    this.metricHistoryGraphsService.analysisHistoryData(this.componentId, analysis);
    this.selectedAnalysisChange.emit(analysis);
  }

  public toggleFactorChart(factor: AnalysisFactor): void {
    this.metricHistoryGraphsService.toggleFactorChart(factor);
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

  private chartData(analysis: IMetricAnalysisModel[], showFactorChart?: Record<AnalysisFactor, boolean>): Array<ChartData> {
    const data: Array<ChartData> = [
      {
        name: this.metricsData?.selectedAnalysis?.metric.name,
        values: analysis?.map(value => this.getChartDataValue(value)) ?? []
      }
    ];

    this.availableFactorCharts.expectedValue = analysis?.some(data => data.analysisValues.expectedValue);
    this.availableFactorCharts.goodValue = analysis?.some(data => data.analysisValues.goodValue);
    this.availableFactorCharts.perfectValue = analysis?.some(data => data.analysisValues.perfectValue);

    showFactorChart?.['expectedValue'] && this.addFactorChartData(data, analysis, MetricStates.WITH_RISK, 'expectedValue');
    showFactorChart?.['goodValue'] && this.addFactorChartData(data, analysis, MetricStates.ACCEPTABLE, 'goodValue');
    showFactorChart?.['perfectValue'] && this.addFactorChartData(data, analysis, MetricStates.PERFECT, 'perfectValue');

    // @howto: disable factor chart inputs if there is no data for that factor
    // Object.keys(this.availableFactorCharts).forEach(value => {
    //   const inputElement = $.get(`.factor-${value} input`) as HTMLInputElement;
    //
    //   if (inputElement) {
    //     inputElement.disabled = !this.availableFactorCharts[value as AnalysisFactor];
    //   }
    // });

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
    valueKey: AnalysisFactor
  ): void {
    if (this.availableFactorCharts[valueKey]) {
      data.push({
        lineStyle: 'dotted',
        smooth: true,
        name: this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[state].style}`)),
        values: analysis?.map(value => this.getFactorChartDataValue(value, state, valueKey)) ?? []
      });
    }
  }

  private getFactorChartDataValue(value: IMetricAnalysisModel, state: MetricStates, valueKey: AnalysisFactor): ChartValue {
    return {
      color: MetricState[state].color,
      value: this.valueTypeToNumberPipe.transform(value.analysisValues[valueKey], this.metricsData?.selectedAnalysis?.metric.valueType) ?? 0
    };
  }
}
