import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { AvailableMetricStates, DEFAULT_METRIC_STATE, stateByCoverage, styleByCoverage } from 'src/app/core/lib';
import { AnalysisType, IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { AnalysisService } from 'src/app/core/services/sdc';

@Component({
  selector: 'sdc-metric-info',
  templateUrl: './sdc-metric-info.component.html',
  styleUrls: ['./sdc-metric-info.component.scss']
})
export class SdcMetricInfoComponent implements OnInit {
  @Input()
  public componentId!: number;

  @Input()
  public metric!: IMetricModel;

  @Input()
  public selected = false;

  @Output()
  public selectMetric: EventEmitter<IMetricAnalysisModel> = new EventEmitter();

  public style = '';
  public analysis?: IMetricAnalysisModel;

  constructor(private analysisService: AnalysisService) {}

  ngOnInit(): void {
    this.analysisService
      .analysis(this.componentId, this.metric.id)
      .then(data => {
        this.analysis = data;
        this.style = styleByCoverage(data.coverage);
      })
      .catch(() => {
        this.analysis = { analysisDate: 0, metric: this.metric, coverage: 0, analysisValues: { metricValue: '' } };
      });
  }

  get icon(): string {
    return {
      [AvailableMetricStates.CRITICAL]: 'fa-solid fa-circle-xmark fa-lg',
      [AvailableMetricStates.WITH_RISK]: 'fa-solid fa-triangle-exclamation fa-lg',
      [AvailableMetricStates.ACCEPTABLE]: 'fa-solid fa-circle-exclamation fa-lg',
      [AvailableMetricStates.PERFECT]: 'fa-solid fa-circle-check fa-lg'
    }[stateByCoverage(this.analysis?.coverage ?? DEFAULT_METRIC_STATE)];
  }

  get analysisIcon(): string {
    return (
      {
        [AnalysisType.GIT]: 'fa-brands fa-github',
        [AnalysisType.SONAR]: 'fa-solid fa-satellite-dish'
      }[this.metric.type] ?? ''
    );
  }

  public onClick() {
    this.selectMetric.emit(this.analysis);
  }
}
