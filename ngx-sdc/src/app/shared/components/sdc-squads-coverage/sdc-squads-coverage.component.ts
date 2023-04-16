import { Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants/app.constants';
import { ISquadModel } from 'src/app/core/models/sdc';

@Component({
  selector: 'sdc-squads-coverage',
  templateUrl: './sdc-squads-coverage.component.html',
  styleUrls: ['./sdc-squads-coverage.component.scss']
})
export class SdcSquadsCoverageComponent implements OnInit, OnDestroy {
  @ViewChild('searchInput', { static: true }) searchInput!: ElementRef;

  @Input()
  public squads?: ISquadModel[];

  @Input()
  public filter?: string;

  @Input()
  public selected?: number;

  @Output()
  public searchSquadChanged: EventEmitter<string> = new EventEmitter();

  @Output()
  public selectSquad: EventEmitter<ISquadModel> = new EventEmitter();

  private subscription$!: Subscription;

  ngOnInit(): void {
    this.subscription$ = this.searchBoxConfig();
  }

  ngOnDestroy(): void {
    this.subscription$.unsubscribe();
  }

  public onClickSquad(squad: ISquadModel) {
    this.selected = squad.id;
    this.selectSquad.emit(squad);
  }

  private searchBoxConfig(): Subscription {
    return fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        map(event => event),
        distinctUntilChanged(),
        debounceTime(DEBOUNCE_TIME)
      )
      .subscribe(() => this.searchSquadChanged.emit(this.filter));
  }
}
