import { Component, Input } from '@angular/core';
import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { Color } from '../../constants/colors';

@Component({
  selector: 'sdc-squad-summary',
  templateUrl: './sdc-squad-summary.component.html',
  styleUrls: ['./sdc-squad-summary.component.scss']
})
export class SdcSquadSummaryComponent {
  @Input()
  public squad!: ISquadModel;

  @Input()
  public components!: IComponentModel[];

  public chartColor = Color.COVERAGE_CHART;
}
