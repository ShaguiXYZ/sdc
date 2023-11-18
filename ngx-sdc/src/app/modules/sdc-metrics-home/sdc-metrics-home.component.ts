import { CommonModule, TitleCasePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NxAccordionModule } from '@aposin/ng-aquila/accordion';
import { NxButtonModule } from '@aposin/ng-aquila/button';
import { NxDialogService, NxModalModule, NxModalRef } from '@aposin/ng-aquila/modal';
import { NxTabsModule } from '@aposin/ng-aquila/tabs';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/components/alert';
import { IComponentModel, IDepartmentModel, IMetricAnalysisModel, ISquadModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { ContextDataService, DateService } from 'src/app/core/services';
import {
  SdcComplianceBarCardComponent,
  SdcMetricInfoComponent,
  SdcMetricsCardsComponent,
  SdcNoDataComponent,
  SdcTimeEvolutionMultichartComponent
} from 'src/app/shared/components';
import { SdcPieChartComponent, SdcTimeEvolutionChartComponent } from 'src/app/shared/components/sdc-charts';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { MetricState, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig } from 'src/app/shared/models';
import { SdcMetricHistoryGraphsComponent } from './components';
import { MetricsDataModel } from './models';
import { SdcMetricsHomeService } from './services';

@Component({
  selector: 'sdc-metrics-home',
  templateUrl: './sdc-metrics-home.component.html',
  styleUrls: ['./sdc-metrics-home.component.scss'],
  providers: [SdcMetricsHomeService, TitleCasePipe],
  standalone: true,
  imports: [
    SdcComplianceBarCardComponent,
    SdcMetricsCardsComponent,
    SdcMetricInfoComponent,
    SdcMetricHistoryGraphsComponent,
    SdcNoDataComponent,
    SdcPieChartComponent,
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
  @ViewChild('metricsCards') templateRef!: TemplateRef<any>;

  public metricsData?: MetricsDataModel;
  public historicalChartConfig!: ChartConfig;
  public lastLanguageDistribution?: string;

  private data$!: Subscription;
  private metricsCardsDialogRef?: NxModalRef<SdcMetricsCardsComponent>;

  constructor(
    private readonly router: Router,
    private readonly alertService: AlertService,
    private readonly contextDataService: ContextDataService,
    private readonly dateService: DateService,
    private readonly dialogService: NxDialogService,
    private readonly sdcMetricsService: SdcMetricsHomeService
  ) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;
      this.applicationCoverageGraphConfig(this.metricsData.historical);

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
        title: `Metrics | ${this.metricsData.component.name ?? ''}`
      });

      this.lastLanguageDistribution =
        (this.metricsData.languageDistribution?.graph.length &&
          this.metricsData.languageDistribution.graph?.[this.metricsData.languageDistribution.graph.length - 1].data) ||
        undefined;
    });

    this.sdcMetricsService.loadInitData();
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public onOpenPanel(): void {
    if (!this.metricsData?.historical) {
      this.sdcMetricsService.historicalComponentData();
    }
  }

  public onMetricAnalysisSelected(analysis: IMetricAnalysisModel): void {
    this.sdcMetricsService.metricAnalysisSeleted = analysis;
  }

  public onTabChage(index: number): void {
    this.sdcMetricsService.tabSelected = index;
  }

  public onRunProcess(): void {
    this.alertService.confirm(
      {
        title: 'Alerts.RunProcess.Title',
        text: 'Alerts.RunProcess.Description'
      },
      this.sdcMetricsService.analyze,
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
    this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, { department });
    this.router.navigate([AppUrls.departments]);
  }

  public squadClicked(squad: ISquadModel): void {
    this.contextDataService.set(ContextDataInfo.SQUADS_DATA, { squad });
    this.router.navigate([AppUrls.squads]);
  }

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
