import { Component, Input } from '@angular/core';
import { styleByCoverage } from 'src/app/core/lib';
import { IComponentModel } from 'src/app/core/models';

@Component({
  selector: 'ui-compliance-bar-card',
  templateUrl: './compliance-bar-card.component.html',
  styleUrls: ['./compliance-bar-card.component.scss']
})
export class UiComplianceBarCardComponent {
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
