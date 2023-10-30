import { TitleCasePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { BACKGROUND_DEPARTMENT_COLOR } from 'src/app/shared/constants';
import { AvailableMetricStates, MetricState, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig } from 'src/app/shared/models';

@Component({
  selector: 'sdc-department-summary',
  templateUrl: './sdc-department-summary.component.html',
  styleUrls: ['./sdc-department-summary.component.scss'],
  providers: [TitleCasePipe]
})
export class SdcDepartmentSummaryComponent {
  @Input()
  public department!: IDepartmentModel;

  public readonly BACKGROUND_DEPARTMENT_COLOR = BACKGROUND_DEPARTMENT_COLOR;
  public chartConfig!: ChartConfig;

  private _squads!: ISquadModel[];

  constructor(private readonly titleCasePipe: TitleCasePipe, private readonly translateService: TranslateService) {}

  @Input()
  public set squads(values: ISquadModel[]) {
    this._squads = [...values];
    this.chartConfig = this.stateCounts();
  }

  private stateCounts(): ChartConfig {
    const counts: { [key: string]: { value: number; color: string } } = {};
    this._squads?.forEach(squad => {
      const state: AvailableMetricStates = stateByCoverage(squad.coverage ?? 0);
      counts[state] = { value: counts[state] ? counts[state].value + 1 : 1, color: MetricState[state].color };
    });

    const keys = Object.keys(counts);

    return {
      axis: {
        yAxis: keys.map(key => this.titleCasePipe.transform(this.translateService.instant(`Component.State.${MetricState[key].style}`)))
      },
      data: keys.map(key => ({
        values: { value: `${counts[key].value}`, color: counts[key].color }
      }))
    };
  }
}
