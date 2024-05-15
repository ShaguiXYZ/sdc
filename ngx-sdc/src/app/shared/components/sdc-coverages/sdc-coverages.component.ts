import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NxCardModule } from '@aposin/ng-aquila/card';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { NxSpinnerModule } from '@aposin/ng-aquila/spinner';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { ICoverageModel } from 'src/app/core/models/sdc';
import { SdcCoverageInfoComponent } from '../sdc-coverage-info';

@Component({
  selector: 'sdc-coverages',
  styleUrls: ['./sdc-coverages.component.scss'],
  templateUrl: './sdc-coverages.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NxCardModule,
    NxHeadlineModule,
    NxInputModule,
    NxSpinnerModule,
    TranslateModule,
    SdcCoverageInfoComponent
  ]
})
export class SdcCoveragesComponent implements OnInit, OnDestroy {
  @Input()
  public title = '';

  @Input()
  public coverages?: ICoverageModel[];

  @Input()
  public filter?: string;

  @Input()
  public selected?: number;

  @Input()
  public selectable = true;

  @Output()
  public searchCoverageChanged: EventEmitter<string> = new EventEmitter();

  @Output()
  public selectCoverage: EventEmitter<ICoverageModel> = new EventEmitter();

  @ViewChild('searchInput', { static: true })
  private searchInput!: ElementRef;
  private subscription$!: Subscription;

  ngOnInit(): void {
    this.subscription$ = this.searchBoxConfig();
  }

  ngOnDestroy(): void {
    this.subscription$.unsubscribe();
  }

  public onClickCoverage(coverage: ICoverageModel): void {
    this.selected = coverage.id;
    this.selectCoverage.emit(coverage);
  }

  private searchBoxConfig(): Subscription {
    return fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        map(event => event),
        distinctUntilChanged(),
        debounceTime(DEBOUNCE_TIME)
      )
      .subscribe(() => this.searchCoverageChanged.emit(this.filter));
  }
}
