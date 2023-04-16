import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IComponentModel, IDepartmentModel } from 'src/app/core/models/sdc';
import { COVERAGE_CHART } from '../../constants/colors';
import { IStateCount } from '../sdc-state-count';

@Component({
  selector: 'sdc-department-summary',
  templateUrl: './sdc-department-summary.component.html',
  styleUrls: ['./sdc-department-summary.component.scss']
})
export class SdcDepartmentSummaryComponent {
  @Input()
  public department!: IDepartmentModel;

  @Input()
  public components!: IComponentModel[];

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public chartColor = COVERAGE_CHART;

  public onClickStateCount(event: IStateCount) {
    this.clickStateCount.emit(event);
  }
}
