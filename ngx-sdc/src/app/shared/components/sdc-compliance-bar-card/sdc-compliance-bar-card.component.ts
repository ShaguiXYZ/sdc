import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { TranslateModule } from '@ngx-translate/core';
import { IComponentModel } from 'src/app/core/models/sdc';
import { styleByCoverage } from '../../lib';

@Component({
  selector: 'sdc-compliance-bar-card',
  templateUrl: './sdc-compliance-bar-card.component.html',
  styleUrls: ['./sdc-compliance-bar-card.component.scss'],
  standalone: true,
  imports: [CommonModule, NxBadgeModule, NxCardModule, NxLinkModule, NxProgressbarModule, TranslateModule]
})
export class SdcComplianceBarCardComponent {
  public barCoverage!: number;
  public coverage!: number;
  public coverageStyle!: string;
  public date?: number;

  private _component!: IComponentModel;

  @Input()
  public showMore = true;

  @Input()
  public hideBorder = false;

  @Input()
  set component(value: IComponentModel) {
    this._component = value;
    this.coverage = Math.round(this._component.coverage ?? 0);
    this.coverageStyle = styleByCoverage(this.coverage);
    this.barCoverage = this.coverage / 100;
  }
  get component(): IComponentModel {
    return this._component;
  }

  @Output()
  public clickLink: EventEmitter<IComponentModel> = new EventEmitter();

  public onClick() {
    this.clickLink.emit(this.component);
  }
}
