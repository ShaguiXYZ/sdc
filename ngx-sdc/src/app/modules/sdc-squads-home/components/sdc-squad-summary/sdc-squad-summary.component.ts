import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { IStateCount } from 'src/app/shared/components';
import { BACKGROUND_GRAPH_COLOR } from 'src/app/shared/constants';

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

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public BACKGROUND_GRAPH_COLOR = BACKGROUND_GRAPH_COLOR;

  public onClickStateCount(event: IStateCount) {
    this.clickStateCount.emit(event);
  }
}
