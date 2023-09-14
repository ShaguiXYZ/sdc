import { Component, Input } from '@angular/core';
import { IMetricAnalysisModel, MetricModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-metrics-cards',
  templateUrl: './sdc-metrics-cards.component.html',
  styleUrls: ['./sdc-metrics-cards.component.scss']
})
export class SdcMetricsCardsComponent {
  @Input()
  public componentId!: number;
}
