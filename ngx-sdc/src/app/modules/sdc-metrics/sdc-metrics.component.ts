import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { MetricsDataModel } from './models/sdc-metrics-data.model';
import { SdcMetricsService } from './services';
import { MetricsContextData } from './models';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService]
})
export class SdcMetricsComponent implements OnInit, OnDestroy {
  public metricsData?: MetricsDataModel;
  public contextData?: MetricsContextData;
  private data$!: Subscription;

  constructor(private sdcMetricsService: SdcMetricsService) {}

  ngOnInit(): void {
    this.contextData = this.sdcMetricsService.context();

    this.data$ = this.sdcMetricsService.onDataChange().subscribe(metricsData => {
      console.log(metricsData);
      this.metricsData = metricsData;
    });
  }

  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }
}
