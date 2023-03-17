import { Component, Input, OnInit } from '@angular/core';
import { IComponentModel } from 'src/app/core/models/sdc';
import { SdcComplianceBarCardsService } from './services';

@Component({
  selector: 'sdc-compliance-bar-cards',
  templateUrl: './sdc-compliance-bar-cards.component.html',
  styleUrls: ['./sdc-compliance-bar-cards.component.scss'],
  providers: [SdcComplianceBarCardsService]
})
export class SdcComplianceBarCardsComponent implements OnInit {
  public components: IComponentModel[] = [];

  private _squad!: number;

  constructor(private complianceBarCardsService: SdcComplianceBarCardsService) {}

  get squad(): number {
    return this._squad;
  }
  @Input()
  set squad(value: number) {
    this._squad = value;
    this.complianceBarCardsService.squadComponents(this.squad);
  }

  ngOnInit(): void {
    this.complianceBarCardsService.onDataChange().subscribe(components => (this.components = components));
  }
}
