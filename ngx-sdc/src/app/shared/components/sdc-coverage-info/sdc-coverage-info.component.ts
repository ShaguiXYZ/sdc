import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ICoverageModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-coverage-info',
  templateUrl: './sdc-coverage-info.component.html',
  styleUrls: ['./sdc-coverage-info.component.scss']
})
export class SdcCoverageInfoComponent {
  @Input()
  public coverage!: ICoverageModel;

  @Input()
  public selectable?: boolean;

  @Input()
  public selected?: boolean;

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  public onClick() {
    this.selectCoverage.emit(this.coverage);
  }
}
