import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { MetricStates, stateByCoverage } from '../../lib';
import { IStateCount } from '../../models';
import { SdcStateCountComponent } from './components';
import { DataInfo } from 'src/app/core/models';

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
    return (
      this.components?.reduce((counts: IStateCount[], component: ICoverageModel) => {
        const state: MetricStates = stateByCoverage(component.coverage ?? 0);
        const existingCount = counts.find(count => count.state === state);

        if (existingCount) {
          existingCount.count++;
        } else {
          counts.push({ state, count: 1 });
        }

        return counts;
      }, []) ?? []
    );
  }
}
