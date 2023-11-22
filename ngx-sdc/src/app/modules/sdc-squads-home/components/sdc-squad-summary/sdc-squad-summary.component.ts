import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcComponentsStateCountComponent, SdcNoDataComponent, SdcTimeEvolutionMultichartComponent } from 'src/app/shared/components';
import { SdcCoverageChartComponent, SdcPieChartComponent } from 'src/app/shared/components/sdc-charts';
import { BACKGROUND_SQUAD_COLOR } from 'src/app/shared/constants';
import { IStateCount } from 'src/app/shared/models';
import { SquadSummaryModel } from './models';
import { SdcSquadSummaryService } from './services';

@Component({
  selector: 'sdc-squad-summary',
  templateUrl: './sdc-squad-summary.component.html',
  styleUrls: ['./sdc-squad-summary.component.scss'],
  providers: [SdcSquadSummaryService],
  standalone: true,
  imports: [
    SdcComponentsStateCountComponent,
    SdcCoverageChartComponent,
    SdcPieChartComponent,
    SdcTimeEvolutionMultichartComponent,
    SdcNoDataComponent,
    CommonModule,
    NxTabsModule,
    TranslateModule
  ]
})
export class SdcSquadSummaryComponent implements OnInit, OnDestroy {
  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public readonly BACKGROUND_SQUAD_COLOR = BACKGROUND_SQUAD_COLOR;
  public squadSummaryData!: SquadSummaryModel;
  public lastLanguageDistribution?: string;
  public chartToShow: 'line' | 'pie' = 'pie';

  private data$!: Subscription;

  constructor(private readonly squadSummaryService: SdcSquadSummaryService) {}

  public get squad(): ISquadModel {
    return this.squadSummaryData.squad;
  }
  @Input()
  public set squad(value: ISquadModel) {
    this.squadSummaryData = { ...this.squadSummaryData, squad: value };
    this.onSquadChage(this.squadSummaryData.selectedTabIndex ?? 0);
  }

  public get components(): IComponentModel[] {
    return this.squadSummaryData.components ?? [];
  }
  @Input()
  public set components(values: IComponentModel[]) {
    this.squadSummaryData = { ...this.squadSummaryData, components: values };
  }

  ngOnInit(): void {
    this.data$ = this.squadSummaryService.onDataChange().subscribe(data => {
      this.squadSummaryData = { ...this.squadSummaryData, ...data };

      this.lastLanguageDistribution =
        (this.squadSummaryData.languageDistribution?.graph.length &&
          this.squadSummaryData.languageDistribution.graph?.[this.squadSummaryData.languageDistribution.graph.length - 1].data) ||
        undefined;
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public onClickStateCount(event: IStateCount) {
    this.clickStateCount.emit(event);
  }

  public onSquadChage(index: number): void {
    this.squadSummaryData.selectedTabIndex = index;
    this.squadSummaryService.tabIndexChange(index, this.squadSummaryData.squad.id);
  }

  public toggleChart(): void {
    this.chartToShow = this.chartToShow === 'line' ? 'pie' : 'line';
  }
}
