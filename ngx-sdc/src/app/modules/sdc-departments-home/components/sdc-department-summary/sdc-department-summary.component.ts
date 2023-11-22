import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcNoDataComponent, SdcTimeEvolutionMultichartComponent } from 'src/app/shared/components';
import { SdcCoverageChartComponent, SdcHorizontalBarChartComponent, SdcPieChartComponent } from 'src/app/shared/components/sdc-charts';
import { BACKGROUND_DEPARTMENT_COLOR } from 'src/app/shared/constants';
import { MetricState, MetricStates, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig } from 'src/app/shared/models';
import { DepartmentSummaryModel } from './models';
import { SdcDepartmentSummaryService } from './services';

@Component({
  selector: 'sdc-department-summary',
  templateUrl: './sdc-department-summary.component.html',
  styleUrls: ['./sdc-department-summary.component.scss'],
  providers: [SdcDepartmentSummaryService, TitleCasePipe],
  standalone: true,
  imports: [
    SdcCoverageChartComponent,
    SdcHorizontalBarChartComponent,
    SdcNoDataComponent,
    SdcPieChartComponent,
    SdcTimeEvolutionMultichartComponent,
    CommonModule,
    NxHeadlineModule,
    NxTabsModule,
    TranslateModule
  ]
})
export class SdcDepartmentSummaryComponent implements OnInit, OnDestroy {
  public readonly BACKGROUND_DEPARTMENT_COLOR = BACKGROUND_DEPARTMENT_COLOR;
  public departmentSummaryData!: DepartmentSummaryModel;
  public lastLanguageDistribution?: string;
  public chartConfig!: ChartConfig;

  private data$!: Subscription;

  constructor(
    private readonly departmentSummaryService: SdcDepartmentSummaryService,
    private readonly titleCasePipe: TitleCasePipe,
    private readonly translateService: TranslateService
  ) {}

  public get department(): IDepartmentModel {
    return this.departmentSummaryData.department;
  }
  @Input()
  public set department(value: IDepartmentModel) {
    this.departmentSummaryData = { ...this.departmentSummaryData, department: value };
    this.onDepartmentChage(this.departmentSummaryData.selectedTabIndex ?? 0);
  }

  ngOnInit(): void {
    this.data$ = this.departmentSummaryService.onDataChange().subscribe(data => {
      this.departmentSummaryData = { ...this.departmentSummaryData, ...data };

      this.lastLanguageDistribution =
        (this.departmentSummaryData.languageDistribution?.graph.length &&
          this.departmentSummaryData.languageDistribution.graph?.[this.departmentSummaryData.languageDistribution.graph.length - 1].data) ||
        undefined;
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  @Input()
  public set squads(values: ISquadModel[]) {
    this.departmentSummaryData = { ...this.departmentSummaryData, squads: values };
    this.chartConfig = this.stateCounts();
  }

  public onDepartmentChage(index: number): void {
    this.departmentSummaryData.selectedTabIndex = index;
    this.departmentSummaryService.tabIndexChange(index, this.departmentSummaryData.department.id);
  }

  private stateCounts(): ChartConfig {
    const counts: { [key: string]: { value: number; color: string } } = {};

    this.departmentSummaryData.squads?.forEach(squad => {
      const state: MetricStates = stateByCoverage(squad.coverage ?? 0);
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
