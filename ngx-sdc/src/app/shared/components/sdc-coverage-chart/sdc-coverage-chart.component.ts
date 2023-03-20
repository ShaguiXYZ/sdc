import { Component, Input } from '@angular/core';
import { ICoverageChartModel } from './models';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent {
  @Input()
  public coverage!: ICoverageChartModel;
  @Input()
  public size?: [number, number];
}
