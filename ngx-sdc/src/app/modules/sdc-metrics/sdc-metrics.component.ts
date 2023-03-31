import { Component, OnInit } from '@angular/core';
import { MetricsContextData } from 'src/app/shared/constants/context-data';
import { SdcMetricsService } from './services';

@Component({
  selector: 'sdc-metrics',
  templateUrl: './sdc-metrics.component.html',
  styleUrls: ['./sdc-metrics.component.scss'],
  providers: [SdcMetricsService]
})
export class SdcMetricsComponent implements OnInit {
  public contextData!: MetricsContextData;

  constructor(private sdcMetricsService: SdcMetricsService) {}

  ngOnInit(): void {
    this.contextData = this.sdcMetricsService.contextData;
  }
}
