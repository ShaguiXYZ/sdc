import { Component, EventEmitter, Input, Output } from '@angular/core';
import { IComplianceModel } from '../sdc-compliance-bar-card/models';

@Component({
  selector: 'sdc-compliance-bar-cards',
  templateUrl: './sdc-compliance-bar-cards.component.html',
  styleUrls: ['./sdc-compliance-bar-cards.component.scss']
})
export class SdcComplianceBarCardsComponent {
  @Input()
  public compliances?: IComplianceModel[];

  @Output()
  public clickLink: EventEmitter<IComplianceModel> = new EventEmitter();

  public onClick(event: IComplianceModel) {
    this.clickLink.emit(event);
  }
}
