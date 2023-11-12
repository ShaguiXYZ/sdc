import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IComplianceModel } from '../../models';
import { SdcComplianceBarCardComponent } from '../sdc-compliance-bar-card/sdc-compliance-bar-card.component';

@Component({
  selector: 'sdc-compliance-bar-cards',
  templateUrl: './sdc-compliance-bar-cards.component.html',
  styleUrls: ['./sdc-compliance-bar-cards.component.scss'],
  standalone: true,
  imports: [CommonModule, SdcComplianceBarCardComponent, TranslateModule]
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
