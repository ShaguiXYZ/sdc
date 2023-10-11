import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AvailableMetricStates, DEFAULT_METRIC_STATE, hasValue, stateByCoverage, styleByCoverage } from 'src/app/core/lib';
import { IMetricAnalysisModel, iconByType } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-metric-info',
  templateUrl: './sdc-metric-info.component.html',
  styleUrls: ['./sdc-metric-info.component.scss']
})
export class SdcMetricInfoComponent {
  @Input()
  public selected = false;

  private _analysis!: IMetricAnalysisModel;
  public get analysis(): IMetricAnalysisModel {
    return this._analysis;
  }
  @Input()
  public set analysis(value: IMetricAnalysisModel) {
    this._analysis = value;
    this.style = styleByCoverage(this.analysis.coverage);
  }

  @Output()
  public selectMetric: EventEmitter<IMetricAnalysisModel> = new EventEmitter();

  public style = '';

  get icon(): string {
    return hasValue(this.analysis?.coverage)
      ? {
          [AvailableMetricStates.CRITICAL]: 'fa-solid fa-circle-xmark fa-lg',
          [AvailableMetricStates.WITH_RISK]: 'fa-solid fa-triangle-exclamation fa-lg',
          [AvailableMetricStates.ACCEPTABLE]: 'fa-solid fa-circle-exclamation fa-lg',
          [AvailableMetricStates.PERFECT]: 'fa-solid fa-circle-check fa-lg'
        }[stateByCoverage(this.analysis.coverage)]
      : 'fa-solid fa-circle-info fa-lg';
  }

  get analysisIcon(): string {
    return iconByType(this.analysis.metric.type);
  }

  public onClick() {
    this.selectMetric.emit(this.analysis);
  }
}
