import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcTimeEvolutionMultichartComponent } from 'src/app/shared/components';
import {
  ChartConfig,
  SdcCoverageChartComponent,
  SdcHorizontalBarChartComponent,
  SdcPieChartComponent
} from 'src/app/shared/components/sdc-charts';
import { BACKGROUND_DEPARTMENT_COLOR } from 'src/app/shared/constants';
import { MetricState, MetricStates, stateByCoverage } from 'src/app/shared/lib';
import { ServiceSummaryModel } from './models';
import { SdcDepartmentSummaryService } from './services';

@Component({
  selector: 'sdc-department-summary',
  styleUrls: ['./sdc-department-summary.component.scss'],
  templateUrl: './sdc-department-summary.component.html',
  providers: [SdcDepartmentSummaryService, TitleCasePipe],
  standalone: true,
  imports: [
    CommonModule,
    NxHeadlineModule,
    NxTabsModule,
    SdcCoverageChartComponent,
    SdcHorizontalBarChartComponent,
    SdcPieChartComponent,
    SdcTimeEvolutionMultichartComponent,
    TranslateModule
  ]
})
export class SdcDepartmentSummaryComponent implements OnInit, OnDestroy {
  public readonly BACKGROUND_DEPARTMENT_COLOR = BACKGROUND_DEPARTMENT_COLOR;
  public serviceSummaryData: ServiceSummaryModel = {};
  public chartConfig!: ChartConfig;
  public lastLanguageDistribution?: string;

  private _department!: IDepartmentModel;
  private _squads: ISquadModel[] = [];
  private _selectedTabIndex = 0;
  private data$!: Subscription;

  constructor(
    private readonly departmentSummaryService: SdcDepartmentSummaryService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService
  ) {}

  ngOnInit(): void {
    this.data$ = this.departmentSummaryService.onDataChange().subscribe(data => {
      this.serviceSummaryData = { ...this.serviceSummaryData, ...data };

      this.lastLanguageDistribution =
        (this.serviceSummaryData.languageDistribution?.graph.length &&
          this.serviceSummaryData.languageDistribution.graph?.[this.serviceSummaryData.languageDistribution.graph.length - 1].data) ||
        undefined;
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public get department(): IDepartmentModel {
    return this._department;
  }
  @Input()
  public set department(value: IDepartmentModel) {
    this._department = value;
    this.selectedTabIndex = this._selectedTabIndex;
  }

  @Input()
  public set squads(values: ISquadModel[]) {
    this._squads = values;
    this.chartConfig = this.stateCounts(this._squads);
  }

  public get selectedTabIndex() {
    return this._selectedTabIndex;
  }
  @Input()
  public set selectedTabIndex(index: number) {
    this._selectedTabIndex = index;
    this.departmentSummaryService.tabFn(this.selectedTabIndex, this.department.id);
  }

  private stateCounts(squads: ISquadModel[]): ChartConfig {
    const counts: { [key in MetricStates]: { value: number; color: string } } = {} as {
      [key in MetricStates]: { value: number; color: string };
    };

    squads?.forEach(squad => {
      const state: MetricStates = stateByCoverage(squad.coverage ?? 0);
      counts[state] = { value: counts[state] ? counts[state].value + 1 : 1, color: MetricState[state].color };
    });

    const keys = Object.keys(counts) as MetricStates[];

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
