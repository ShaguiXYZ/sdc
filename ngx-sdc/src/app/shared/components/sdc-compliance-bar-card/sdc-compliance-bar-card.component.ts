import { Component, EventEmitter, Input, Output } from '@angular/core';
import { styleByCoverage } from '../../lib';
import { IComplianceModel } from '../../models';
import { CommonModule } from '@angular/common';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { TranslateModule } from '@ngx-translate/core';

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

  private _compliance!: IComplianceModel;

  @Input()
  public showMore = true;

  @Input()
  public hideBorder = false;

  @Input()
  set compliance(compliance: IComplianceModel) {
    this._compliance = compliance;
    this.coverage = Math.round(this._compliance.coverage ?? 0);
    this.coverageStyle = styleByCoverage(this.coverage);
    this.barCoverage = this.coverage / 100;
  }
  get compliance(): IComplianceModel {
    return this._compliance;
  }

  @Output()
  public clickLink: EventEmitter<IComplianceModel> = new EventEmitter();

  public onClick() {
    this.clickLink.emit(this.compliance);
  }
}
