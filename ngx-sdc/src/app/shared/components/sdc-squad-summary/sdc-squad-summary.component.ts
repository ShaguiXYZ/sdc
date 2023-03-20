import { Component, Input } from '@angular/core';
import { ISquadModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-squad-summary',
  templateUrl: './sdc-squad-summary.component.html',
  styleUrls: ['./sdc-squad-summary.component.scss']
})
export class SdcSquadSummaryComponent {
  @Input()
  public squad!: ISquadModel;
}
