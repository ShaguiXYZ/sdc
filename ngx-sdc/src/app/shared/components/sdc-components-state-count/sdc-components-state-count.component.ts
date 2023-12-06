import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { MetricStates, stateByCoverage } from '../../lib';
import { IStateCount } from '../../models';
import { SdcStateCountComponent } from './components';

@Component({
  selector: 'sdc-components-state-count',
  template: `
    @for (count of counts; track count.state) {
      <div [ngClass]="{ 'nx-margin-bottom-2xs': !$last }">
        <sdc-state-count [stateCount]="count" (clickStateCount)="onClick($event)"></sdc-state-count>
      </div>
    }
  `,
  standalone: true,
  imports: [CommonModule, SdcStateCountComponent]
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
      const state: MetricStates = stateByCoverage(component.coverage ?? 0);

      if (counts[state]) {
        counts[state] = { ...counts[state], count: counts[state].count + 1 };
      } else {
        counts[state] = { state, count: 1 };
      }
    });

    return Object.values(counts);
  }
}
