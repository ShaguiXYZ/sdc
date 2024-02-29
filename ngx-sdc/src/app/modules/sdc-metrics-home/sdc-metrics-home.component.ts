import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/components/alert';
import { IComponentModel, IDepartmentModel, IMetricAnalysisModel, ISquadModel, ITagModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { ContextDataService, DateService, IfRoleDirective } from 'src/app/core/services';
import { SdcRouteService } from 'src/app/core/services/sdc';
import {
  SdcComplianceBarCardComponent,
  SdcMetricInfoComponent,
  SdcMetricsCardsComponent,
  SdcTagsComponent,
  SdcTimeEvolutionMultichartComponent
} from 'src/app/shared/components';
import { ChartConfig, SdcPieChartComponent, SdcTimeEvolutionChartComponent } from 'src/app/shared/components/sdc-charts';
import { ContextDataInfo } from 'src/app/shared/constants';
import { MetricState, stateByCoverage } from 'src/app/shared/lib';
import { SdcMetricHistoryGraphsComponent } from './components';
import { MetricsDataModel } from './models';
import { SdcMetricsHomeService } from './services';

@Component({
  selector: 'sdc-metrics-home',
  styleUrls: ['./sdc-metrics-home.component.scss'],
  templateUrl: './sdc-metrics-home.component.html',
  providers: [SdcMetricsHomeService, TitleCasePipe],
  standalone: true,
  imports: [
    IfRoleDirective,
    SdcComplianceBarCardComponent,
    SdcMetricHistoryGraphsComponent,
    SdcMetricInfoComponent,
    SdcMetricsCardsComponent,
    SdcPieChartComponent,
    SdcTagsComponent,
    SdcTimeEvolutionChartComponent,
    SdcTimeEvolutionMultichartComponent,
    CommonModule,
    NxAccordionModule,
    NxButtonModule,
    NxModalModule,
    NxTabsModule,
    NxTooltipModule,
    TranslateModule
  ]
})
export class SdcMetricsHomeComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsDataModel;
  public historicalChartConfig!: ChartConfig;
  public lastLanguageDistribution?: string;

  @ViewChild('metricsCards')
  private templateRef!: TemplateRef<any>;
  private data$!: Subscription;
  private metricsCardsDialogRef?: NxModalRef<SdcMetricsCardsComponent>;

  constructor(
    private readonly alertService: AlertService,
    private readonly contextDataService: ContextDataService,
    private readonly dateService: DateService,
    private readonly dialogService: NxDialogService,
    private readonly metricsService: SdcMetricsHomeService,
    private readonly routerService: SdcRouteService
  ) {}

  ngOnInit(): void {
    this.metricsService.loadInitData().then(this.populateData);
    this.data$ = this.metricsService.onDataChange().subscribe(this.populateData);
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public onOpenPanel(): void {
    if (!this.metricsData?.historical) {
      this.metricsService.historicalComponentData();
    }
  }

  public onMetricAnalysisSelected(analysis: IMetricAnalysisModel): void {
    this.metricsService.metricAnalysisSeleted = analysis;
  }

  public onTabChage(index: number): void {
    this.metricsService.tabSelected = index;
  }

  public onRunProcess(): void {
    this.alertService.confirm(
      {
        title: 'Alerts.RunProcess.Title',
        text: 'Alerts.RunProcess.Description'
      },
      this.metricsService.analyze,
      { okText: 'Label.Yes', cancelText: 'Label.No' }
    );
  }

  public openMetricsCards(): void {
    this.metricsCardsDialogRef = this.dialogService.open(this.templateRef, {
      appearance: 'expert',
      data: { component: this.metricsData?.component }
    });
  }

  public closeMetricsCards(): void {
    this.metricsCardsDialogRef?.close();
  }

  public departmentClicked(department: IDepartmentModel): void {
    this.routerService.toDepartment(department);
  }

  public squadClicked(squad: ISquadModel): void {
    this.routerService.toSquad(squad);
  }

  public onTagAdd(tag: ITagModel): void {
    this.metricsService.addTag(tag);
  }

  public onTagRemove(tag: ITagModel): void {
    this.metricsService.removeTag(tag);
  }

  private populateData = (metricsData: MetricsDataModel): void => {
    this.metricsData = metricsData;
    this.applicationCoverageGraphConfig(this.metricsData.historical);

    this.lastLanguageDistribution =
      (this.metricsData.languageDistribution?.graph.length &&
        this.metricsData.languageDistribution.graph?.[this.metricsData.languageDistribution.graph.length - 1].data) ||
      undefined;

    this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
      ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
      title: `Metrics | ${this.metricsData.component.name ?? ''}`
    });
  };

  private applicationCoverageGraphConfig(historical?: IHistoricalCoverage<IComponentModel>): void {
    const analysis = historical?.historical.page ?? [];

    this.historicalChartConfig = {
      axis: {
        xAxis: analysis?.map(data => this.dateService.dateFormat(data.analysisDate))
      },
      data: [
        {
          values:
            analysis?.map(value => ({
              value: `${value.coverage}`,
              color: MetricState[stateByCoverage(value.coverage)].color
            })) ?? []
        }
      ],
      options: { showVisualMap: true },
      type: ValueType.NUMERIC
    };
  }
}
