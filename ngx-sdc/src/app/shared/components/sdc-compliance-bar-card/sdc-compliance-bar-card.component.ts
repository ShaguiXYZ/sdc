import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { styleByCoverage } from 'src/app/core/lib';
import { IComplianceModel } from './models';

@Component({
  selector: 'sdc-compliance-bar-card',
  templateUrl: './sdc-compliance-bar-card.component.html',
  styleUrls: ['./sdc-compliance-bar-card.component.scss']
})
export class SdcComplianceBarCardComponent implements OnInit {
  public barCoverage!: number;
  public coverage!: number;
  public coverageStyle!: string;
  public date?: Date;

  private _compliance!: IComplianceModel;

  @Input()
  public showMore = true;

  @Input()
  set compliance(compliance: IComplianceModel) {
    this._compliance = compliance;
    this.coverage = Math.round(this._compliance.coverage || 0);
    this.barCoverage = this.coverage / 100;
  }
  get compliance(): IComplianceModel {
    return this._compliance;
  }

  @Output()
  public clickLink: EventEmitter<IComplianceModel> = new EventEmitter();

  ngOnInit(): void {
  this.coverageStyle = styleByCoverage(this.coverage);
  }

  public onClick() {
    this.clickLink.emit(this.compliance);
  }
}
