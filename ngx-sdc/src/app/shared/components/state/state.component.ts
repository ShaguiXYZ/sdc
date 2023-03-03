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

  @Input()
  public component!: IComponentModel;

  constructor(private stateService: StateService) {}
}
