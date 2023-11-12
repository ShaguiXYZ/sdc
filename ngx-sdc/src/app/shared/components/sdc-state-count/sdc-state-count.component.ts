import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { MetricConfig, MetricState, styleByMetricState } from '../../lib';
import { IStateCount } from './model';

@Component({
  selector: 'sdc-state-count',
  templateUrl: './sdc-state-count.component.html',
  styleUrls: ['./sdc-state-count.component.scss'],
  standalone: true,
  imports: [CommonModule, NxLinkModule, TranslateModule]
})
export class SdcStateCountComponent implements OnInit {
  @Input()
  public stateCount!: IStateCount;

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public stateStyle!: string;

  ngOnInit(): void {
    this.stateStyle = styleByMetricState(this.stateCount.state);
  }

  public get metricConfig(): MetricConfig {
    return MetricState[this.stateCount.state];
  }

  public onClick(): void {
    this.clickStateCount.emit(this.stateCount);
  }
}
