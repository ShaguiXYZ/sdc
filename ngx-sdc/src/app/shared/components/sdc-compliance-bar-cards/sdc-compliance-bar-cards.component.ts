import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IComponentModel } from 'src/app/core/models/sdc';
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
  public components?: IComponentModel[];

  @Output()
  public clickLink: EventEmitter<IComponentModel> = new EventEmitter();

  public onClick(event: IComponentModel) {
    this.clickLink.emit(event);
  }
}
