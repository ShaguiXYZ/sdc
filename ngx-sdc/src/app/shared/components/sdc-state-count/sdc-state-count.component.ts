import { Component, Input } from '@angular/core';
import { MetricConfig, MetricState, styleByMetricState } from 'src/app/core/lib';
import { IStateCount } from './model';

@Component({
  selector: 'sdc-state-count',
  templateUrl: './sdc-state-count.component.html',
  styleUrls: ['./sdc-state-count.component.scss']
})
export class SdcStateCountComponent {
  @Input()
  public stateCount!: IStateCount;

  public styleByMetricState = styleByMetricState;

  public get metricConfig(): MetricConfig {
    return MetricState[this.stateCount.state];
  }
}
