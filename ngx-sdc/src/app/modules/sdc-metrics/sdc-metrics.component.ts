import { TitleCasePipe } from '@angular/common';
import { Component, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { NxDialogService, NxModalRef } from '@aposin/ng-aquila/modal';
import { Subscription } from 'rxjs';
import { UiAlertService } from 'src/app/core/components/alert';
import { IComponentModel, IMetricAnalysisModel, ValueType } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { UiContextDataService, UiDateService } from 'src/app/core/services';
import { SdcMetricsCardsComponent } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { MetricState, stateByCoverage } from 'src/app/shared/lib';
import { ChartConfig } from 'src/app/shared/models';
import { MetricsDataModel } from './models';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, TitleCasePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  @ViewChild('metricsCards') templateRef!: TemplateRef<any>;

  public metricsData?: MetricsDataModel;
  public historicalChartConfig!: ChartConfig;

  private data$!: Subscription;
  private metricsCardsDialogRef?: NxModalRef<SdcMetricsCardsComponent>;

  constructor(
    private readonly alertService: UiAlertService,
    private readonly contextDataService: UiContextDataService,
    private readonly dateService: UiDateService,
    private readonly dialogService: NxDialogService,
    private readonly sdcMetricsService: SdcMetricsService
  ) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;
      this.applicationCoverageGraphConfig(this.metricsData.historical);

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
        title: `Metrics | ${this.metricsData.compliance.name ?? ''}`
      });
    });

    this.sdcMetricsService.loadInitData();
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  onOpenPanel(): void {
    if (!this.metricsData?.historical) {
      this.sdcMetricsService.historicalComponentData();
    }
  }

  onMetricAnalysisSelected(analysis: IMetricAnalysisModel): void {
    this.sdcMetricsService.metricAnalysisSeleted = analysis;
  }

  onRunProcess(): void {
    this.alertService.confirm(
      {
        title: 'Alerts.RunProcess.Title',
        text: 'Alerts.RunProcess.Description'
      },
      this.sdcMetricsService.analyze,
      { okText: 'Label.Yes', cancelText: 'Label.No' }
    );
  }

  openMetricsCards(): void {
    this.metricsCardsDialogRef = this.dialogService.open(this.templateRef, {
      appearance: 'expert',
      data: { component: this.metricsData?.compliance }
    });
  }

  closeMetricsCards(): void {
    this.metricsCardsDialogRef?.close();
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
