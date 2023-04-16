import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ISquadModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-squad-coverage',
  templateUrl: './sdc-squad-coverage.component.html',
  styleUrls: ['./sdc-squad-coverage.component.scss']
})
export class SdcSquadCoverageComponent {
  @Input()
  public squad!: ISquadModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectSquad: EventEmitter<ISquadModel> = new EventEmitter();

  public onClick() {
    this.selectSquad.emit(this.squad);
  }
}
