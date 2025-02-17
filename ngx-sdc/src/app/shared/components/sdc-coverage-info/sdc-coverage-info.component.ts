import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, Input, Output } from '@angular/core';
import { NxCopytextModule } from '@aposin/ng-aquila/copytext';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { BACKGROUND_CHART_COLOR } from '../../constants';
import { SdcCoverageChartComponent } from '../sdc-charts';
import { SdcTrendComponent } from '../sdc-trend';

@Component({
    selector: 'sdc-coverage-info',
    styleUrls: ['./sdc-coverage-info.component.scss'],
    templateUrl: './sdc-coverage-info.component.html',
    changeDetection: ChangeDetectionStrategy.OnPush,
    imports: [CommonModule, NxCopytextModule, SdcCoverageChartComponent, SdcTrendComponent]
})
export class SdcCoverageInfoComponent {
  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  public BACKGROUND_CHART_COLOR = BACKGROUND_CHART_COLOR;

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }
}
