import { Component, Input } from '@angular/core';
import { AvailableMetricStates, MetricState, stateByCoverage } from 'src/app/core/lib';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { ChartValue } from '../sdc-horizontal-bar-chart';

@Component({
  selector: 'sdc-department-summary',
  templateUrl: './sdc-department-summary.component.html',
  styleUrls: ['./sdc-department-summary.component.scss']
})
export class SdcDepartmentSummaryComponent {
  public chartValues: ChartValue[] = [];

  @Input()
  public department!: IDepartmentModel;

  private _squads!: ISquadModel[];
  @Input()
  public set squads(values: ISquadModel[]) {
    this._squads = [...values];
    this.chartValues = this.stateCounts();
  }
  public get squads(): ISquadModel[] {
    return this._squads;
  }

  private stateCounts(): ChartValue[] {
    const counts: { [key: string]: { value: number; color: string } } = {};
    this.squads?.forEach(squad => {
      const state: AvailableMetricStates = stateByCoverage(squad.coverage || 0);

      if (counts[state]) {
        counts[state] = { value: counts[state].value + 1, color: MetricState[state].color };
      } else {
        counts[state] = { value: 1, color: MetricState[state].color };
      }
    });

    const result: ChartValue[] = Object.keys(counts).map(key => ({
      yAxis: MetricState[key].style,
      data: `${counts[key].value}`,
      color: counts[key].color
    }));

    return result;
  }
}
