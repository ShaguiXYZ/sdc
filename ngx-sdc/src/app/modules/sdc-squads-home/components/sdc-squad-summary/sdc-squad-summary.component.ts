import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { Subscription } from 'rxjs';
import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { IStateCount } from 'src/app/shared/components';
import { BACKGROUND_SQUAD_COLOR } from 'src/app/shared/constants';
import { SquadSummaryModel } from './models';
import { SdcSquadSummaryService } from './services';

@Component({
  selector: 'sdc-squad-summary',
  templateUrl: './sdc-squad-summary.component.html',
  styleUrls: ['./sdc-squad-summary.component.scss'],
  providers: [SdcSquadSummaryService]
})
export class SdcSquadSummaryComponent implements OnInit, OnDestroy {
  @Input()
  public squad!: ISquadModel;

  @Output()
  public clickStateCount: EventEmitter<IStateCount> = new EventEmitter();

  public readonly BACKGROUND_SQUAD_COLOR = BACKGROUND_SQUAD_COLOR;
  public squadSummaryData: SquadSummaryModel = {};

  private data$!: Subscription;

  constructor(private readonly squadSummaryService: SdcSquadSummaryService) {}

  public get components(): IComponentModel[] {
    return this.squadSummaryData.components ?? [];
  }
  @Input()
  public set components(values: IComponentModel[]) {
    this.squadSummaryData = { ...this.squadSummaryData, components: values };
  }

  ngOnInit(): void {
    this.data$ = this.squadSummaryService.onDataChange().subscribe(data => {
      this.squadSummaryData = { ...this.squadSummaryData, ...data };
    });
  }
  ngOnDestroy(): void {
    this.data$.unsubscribe();
  }

  public onClickStateCount(event: IStateCount) {
    this.clickStateCount.emit(event);
  }

  onTabChage(index: number): void {
    this.squadSummaryService.tabSelected = index;
  }
}
