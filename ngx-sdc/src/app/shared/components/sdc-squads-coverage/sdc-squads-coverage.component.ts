import { Component, Input } from '@angular/core';
import { ISquadModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-squads-coverage',
  templateUrl: './sdc-squads-coverage.component.html',
  styleUrls: ['./sdc-squads-coverage.component.scss']
})
export class SdcSquadsCoverageComponent {
  @Input()
  public squads?: ISquadModel[];
}
