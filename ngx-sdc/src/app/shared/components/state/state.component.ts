import { Component, OnDestroy, OnInit } from '@angular/core';
import { StateService } from './state.service';

@Component({
  selector: 'ui-state',
  templateUrl: './state.component.html',
  styleUrls: ['./state.component.scss'],
  providers: [StateService]
})
export class UiStateComponent implements OnInit, OnDestroy {
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }
}
