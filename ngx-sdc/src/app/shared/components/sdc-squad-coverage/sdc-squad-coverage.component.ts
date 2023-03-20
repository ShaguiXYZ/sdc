import { Component, Input } from '@angular/core';
import { ISquadModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-squad-coverage',
  templateUrl: './sdc-squad-coverage.component.html',
  styleUrls: ['./sdc-squad-coverage.component.scss']
})
export class SdcSquadCoverageComponent {
  @Input()
  public squad!: ISquadModel;
}
