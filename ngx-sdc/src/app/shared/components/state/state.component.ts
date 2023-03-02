import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { IComponentModel } from 'src/app/core/models';
import { StateService } from './state.service';

@Component({
  selector: 'ui-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.scss'],
  providers: [StateService]
})
export class UiStateComponent {
  public coverage?: number;

  private _component!: IComponentModel;

  constructor(private stateService: StateService) {}

  @Input()
  set component(component: IComponentModel) {
    this._component = component;
    this.stateService.componetCoverage(component.id).then(coverage => (this.coverage = coverage));
  }
  get component(): IComponentModel {
    return this._component;
  }
}
