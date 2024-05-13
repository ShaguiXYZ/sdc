import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { hasValue } from '@shagui/ng-shagui/core';
import { AnalysisType, IMetricAnalysisModel, IUriModel, UriType, iconByType, uriTypeByAnalysisType } from 'src/app/core/models/sdc';
import { MetricStates, stateByCoverage, styleByCoverage } from '../../lib';
import { SdcMetricInfoService } from './services';

@Component({
  selector: 'sdc-metric-info',
  styleUrls: ['./sdc-metric-info.component.scss'],
  templateUrl: './sdc-metric-info.component.html',
  providers: [SdcMetricInfoService],
  standalone: true,
  imports: [CommonModule]
})
export class SdcMetricInfoComponent {
  @Input()
  public selected = false;

  @Output()
  public selectMetric: EventEmitter<IMetricAnalysisModel> = new EventEmitter();

  public style = '';

  private _analysis!: IMetricAnalysisModel;

  constructor(private readonly metricInfoService: SdcMetricInfoService) {}

  public get analysis(): IMetricAnalysisModel {
    return this._analysis;
  }
  @Input()
  public set analysis(value: IMetricAnalysisModel) {
    this._analysis = value;
    this.style = styleByCoverage(this.analysis.coverage);
  }

  get icon(): string {
    return hasValue(this.analysis?.coverage)
      ? {
          [MetricStates.CRITICAL]: 'fa-solid fa-circle-xmark fa-lg',
          [MetricStates.WITH_RISK]: 'fa-solid fa-triangle-exclamation fa-lg',
          [MetricStates.ACCEPTABLE]: 'fa-solid fa-circle-exclamation fa-lg',
          [MetricStates.PERFECT]: 'fa-solid fa-circle-check fa-lg'
        }[stateByCoverage(this.analysis.coverage)]
      : 'fa-solid fa-circle-info fa-lg';
  }

  get analysisIcon(): string {
    return iconByType(this.analysis.metric.type);
  }

  public onClick() {
    this.selectMetric.emit(this.analysis);
  }

  public navToSource() {
    this.uriByType(this.analysis.metric.type)
      .then(uri => window.open(uri.url, '_blank'))
      .catch(console.error);
  }

  private uriByType = (type: AnalysisType): Promise<IUriModel> => this.metricInfoService.componentUriByType(uriTypeByAnalysisType(type));
}
