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
import { ServiceSummaryModel } from './models';
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
  public readonly BACKGROUND_SQUAD_COLOR = BACKGROUND_SQUAD_COLOR;
  public serviceSummaryData: ServiceSummaryModel = {};
  public lastLanguageDistribution?: string;
  public chartToShow: 'line' | 'pie' = 'pie';

  @Input()
  public components: IComponentModel[] = [];

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  private _selectedTabIndex = 0;
  private _squad!: ISquadModel;
  private data$!: Subscription;

  constructor(private readonly squadSummaryService: SdcSquadSummaryService) {}

  ngOnInit(): void {
    this.data$ = this.squadSummaryService.onDataChange().subscribe(data => {
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

  public get squad(): ISquadModel {
    return this._squad;
  }
  @Input()
  public set squad(value: ISquadModel) {
    this._squad = value;
    this.selectedTabIndex = this._selectedTabIndex;
  }

  public get selectedTabIndex() {
    return this._selectedTabIndex;
  }
  @Input()
  public set selectedTabIndex(index: number) {
    this._selectedTabIndex = index;
    this.squad && this.squadSummaryService.tabFn(this._selectedTabIndex, this.squad.id);
  }

  public onClickStateCount(event: IStateCount) {
    this.clickStateCount.emit(event);
  }

  public toggleChart(): void {
    this.chartToShow = this.chartToShow === 'line' ? 'pie' : 'line';
  }
}
