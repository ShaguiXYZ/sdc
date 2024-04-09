import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NxBadgeModule } from '@aposin/ng-aquila/badge';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxProgressbarModule } from '@aposin/ng-aquila/progressbar';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, NotificationService, copyToClipboard } from '@shagui/ng-shagui/core';
import { IComponentModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { styleByCoverage } from '../../lib';
import { SdcTrendComponent } from '../sdc-trend';

@Component({
  selector: 'sdc-compliance-bar-card',
  styleUrls: ['./sdc-compliance-bar-card.component.scss'],
  templateUrl: './sdc-compliance-bar-card.component.html',
  standalone: true,
  imports: [CommonModule, NxBadgeModule, NxCardModule, NxLinkModule, NxProgressbarModule, SdcTrendComponent, TranslateModule]
})
export class SdcComplianceBarCardComponent {
  public barCoverage?: number;
  public coverage?: number;
  public coverageStyle?: string;
  public date?: number;

  private _component!: IComponentModel;

  @Input()
  public showMore = true;

  @Input()
  public showSquad = true;

  @Input()
  public showDepartment = true;

  @Input()
  public hideBorder = false;

  constructor(
    private readonly notificationService: NotificationService,
    private readonly translateService: TranslateService
  ) {}

  @Input()
  set component(value: IComponentModel) {
    this._component = value;
    this.coverage = this._component.coverage && Math.round(this._component.coverage);

    if (this.coverage) {
      this.coverageStyle = styleByCoverage(this.coverage);
      this.barCoverage = this.coverage / 100;
    }
  }
  get component(): IComponentModel {
    return this._component;
  }

  @Output()
  public clickLink: EventEmitter<IComponentModel> = new EventEmitter();

  @Output()
  public clickSquad: EventEmitter<ISquadModel> = new EventEmitter();

  @Output()
  public clickDepartment: EventEmitter<IDepartmentModel> = new EventEmitter();

  public onClickShwoMore() {
    this.clickLink.emit(this.component);
  }

  public onClickDepartment() {
    this.clickDepartment.emit(this.component.squad.department);
  }

  public onClickSquad() {
    this.clickSquad.emit(this.component.squad);
  }

  public copyToClipboard(): void {
    copyToClipboard(this.component.name).then(() => {
      this.notificationService.info(
        this.translateService.instant('Label.CopyToClipboard'),
        this.component.name,
        DEFAULT_TIMEOUT_NOTIFICATIONS,
        false
      );
    });
  }
}
