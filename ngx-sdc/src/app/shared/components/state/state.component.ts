import { Component, Input } from '@angular/core';
import { IComponentModel } from 'src/app/core/models';

@Component({
  selector: 'ui-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.scss'],
  providers: []
})
export class UiStateComponent {
  public coverage?: number;

  @Input()
  public component!: IComponentModel;
}
