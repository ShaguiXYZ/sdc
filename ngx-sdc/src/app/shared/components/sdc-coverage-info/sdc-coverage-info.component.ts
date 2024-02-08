import { CommonModule } from '@angular/common';
import { Component, ElementRef, EventEmitter, Input, Output } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { BACKGROUND_CHART_COLOR } from '../../constants';
import { SdcCoverageChartComponent } from '../sdc-charts';
import { SdcTrendComponent } from '../sdc-trend';

@Component({
  selector: 'sdc-coverage-info',
  styleUrls: ['./sdc-coverage-info.component.scss'],
  templateUrl: './sdc-coverage-info.component.html',
  standalone: true,
  imports: [CommonModule, NxCopytextModule, SdcCoverageChartComponent, SdcTrendComponent]
})
export class SdcCoverageInfoComponent {
  public BACKGROUND_CHART_COLOR = BACKGROUND_CHART_COLOR;

  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  private observer!: IntersectionObserver;
  private coverageChartVisible = false;

  constructor(private readonly element: ElementRef) {}

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }
}
