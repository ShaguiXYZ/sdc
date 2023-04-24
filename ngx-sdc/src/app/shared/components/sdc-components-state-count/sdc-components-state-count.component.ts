import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AvailableMetricStates, stateByCoverage } from 'src/app/core/lib';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { IStateCount } from '../sdc-state-count/model/state-count.model';
@Component({
  selector: 'sdc-components-state-count',
  templateUrl: './sdc-components-state-count.component.html',
  styleUrls: ['./sdc-components-state-count.component.scss']
})
export class SdcComponentsStateCountComponent {
  public _components!: ICoverageModel[];
  public counts: IStateCount[] = [];

  get components() {
    return this._components;
  }
  @Input()
  set components(value: ICoverageModel[]) {
    this._components = value;
    this.counts = this.stateCounts();
  }

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public onClick(event: IStateCount): void {
    this.clickStateCount.emit(event);
  }

  private stateCounts(): IStateCount[] {
    const counts: { [key: string]: IStateCount } = {};

    this.components?.forEach(component => {
      const state: AvailableMetricStates = stateByCoverage(component.coverage || 0);

      if (counts[state]) {
        counts[state] = { ...counts[state], count: counts[state].count + 1 };
      } else {
        counts[state] = { state, count: 1 };
      }
    });

    return Object.values(counts);
  }
}
