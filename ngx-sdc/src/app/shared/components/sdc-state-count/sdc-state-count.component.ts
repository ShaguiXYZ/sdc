import { Component, Input, OnInit } from '@angular/core';
import { MetricConfig, MetricState, styleByMetricState } from 'src/app/core/lib';
import { IStateCount } from './model';

@Component({
  selector: 'sdc-state-count',
  templateUrl: './sdc-state-count.component.html',
  styleUrls: ['./sdc-state-count.component.scss']
})
export class SdcStateCountComponent implements OnInit {
  @Input()
  public stateCount!: IStateCount;
  public stateStyle!: string;

  ngOnInit(): void {
    this.stateStyle = styleByMetricState(this.stateCount.state);
  }

  public get metricConfig(): MetricConfig {
    return MetricState[this.stateCount.state];
  }
}
