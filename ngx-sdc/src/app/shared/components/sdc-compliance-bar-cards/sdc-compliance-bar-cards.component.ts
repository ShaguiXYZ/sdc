import { Component, Input } from '@angular/core';
import { IComponentModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-compliance-bar-cards',
  templateUrl: './sdc-compliance-bar-cards.component.html',
  styleUrls: ['./sdc-compliance-bar-cards.component.scss']
})
export class SdcComplianceBarCardsComponent {
  @Input()
  public components?: IComponentModel[];
}
