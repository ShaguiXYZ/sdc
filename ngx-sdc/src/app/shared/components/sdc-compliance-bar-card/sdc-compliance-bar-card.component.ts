import { Component, Input } from '@angular/core';
import { styleByCoverage } from 'src/app/core/lib';
import { IComponentModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-compliance-bar-card',
  templateUrl: './sdc-compliance-bar-card.component.html',
  styleUrls: ['./sdc-compliance-bar-card.component.scss']
})
export class SdcComplianceBarCardComponent {
  public barCoverage!: number;
  public coverage!: number;
  public date?: Date;

  public styleByCoverage = styleByCoverage;

  private _component!: IComponentModel;

  @Input()
  set component(component: IComponentModel) {
    this._component = component;
    this.coverage = Math.round(this._component.coverage || 0);
    this.barCoverage = this.coverage / 100;
  }
  get component(): IComponentModel {
    return this._component;
  }
}
