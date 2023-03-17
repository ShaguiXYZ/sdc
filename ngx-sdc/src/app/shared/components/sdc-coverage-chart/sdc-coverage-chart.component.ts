import { Component, Input } from '@angular/core';
import { Color } from '@swimlane/ngx-charts';
import { ICoverageChartModel } from './models';

@Component({
  selector: 'sdc-coverage-chart',
  templateUrl: './sdc-coverage-chart.component.html',
  styleUrls: ['./sdc-coverage-chart.component.scss']
})
export class SdcCoverageChartComponent {
  @Input()
  public coverage!: ICoverageChartModel;

  colorScheme: string | Partial<Color> = {
    domain: ['#5AA454', '#E44D25', '#CFC0BB', '#7aa3e5', '#a8385d', '#aae3f5']
  };

  constructor() {
    Object.assign(this, { single: this.coverage });
  }
}
