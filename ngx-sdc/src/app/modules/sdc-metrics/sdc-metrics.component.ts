import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { SdcMetricsService } from './services';
import { IMetricModel } from 'src/app/core/models/sdc';
import { MetricsContextData, MetricsDataModel } from './models';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService, DatePipe]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsDataModel;
  public contextData?: MetricsContextData;
  public selected!: IMetricModel;
  public values: { xAxis: string; data: number }[] = [];

  private data$!: Subscription;

  constructor(private datePipe: DatePipe, private sdcMetricsService: SdcMetricsService) {}

  ngOnInit(): void {
    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      this.metricsData = metricsData;

      this.values = this.metricsData.analysis?.map(analysis => ({
        xAxis: this.datePipe.transform(analysis.analysisDate, 'mm/yyyy') || '',
        data: Number(analysis.analysisValues.metricValue)
      })) || [];
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  loadGraphData(metric: IMetricModel) {
    this.selected = metric;
    this.sdcMetricsService.analysisData(metric.id);
  }
}
