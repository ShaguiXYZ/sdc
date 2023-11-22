import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { IComponentModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
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
  public animation = true;

  @Input()
  public components?: IComponentModel[];

  @Input()
  public showSquad = true;

  @Input()
  public showDepartment = true;

  @Output()
  public clickLink: EventEmitter<IComponentModel> = new EventEmitter();

  @Output()
  public clickSquad: EventEmitter<ISquadModel> = new EventEmitter();

  @Output()
  public clickDepartment: EventEmitter<IDepartmentModel> = new EventEmitter();

  public onClickShowMore(event: IComponentModel) {
    this.clickLink.emit(event);
  }

  public onClickSquad(event: ISquadModel) {
    this.clickSquad.emit(event);
  }

  public onClickSDepartment(event: IDepartmentModel) {
    this.clickDepartment.emit(event);
  }
}
