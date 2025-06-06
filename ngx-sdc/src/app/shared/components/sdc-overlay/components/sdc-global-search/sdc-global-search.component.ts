import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { NxCheckboxModule } from '@aposin/ng-aquila/checkbox';
import { TranslateModule } from '@ngx-translate/core';
import { $ } from '@shagui/ng-shagui/core';
import { Subject, Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { ISummaryViewModel, SummaryViewType } from 'src/app/core/models/sdc';
import { BACKGROUND_CHART_COLOR } from 'src/app/shared/constants';
import { SdcCoverageChartComponent } from '../../../sdc-charts';
import { SdcTagComponent } from '../../../sdc-tag';
import { OverlayItemStatus } from '../../models';
import { SdcGlobalSearchService } from './services';

@Component({
    selector: 'sdc-global-search',
    styleUrls: ['./sdc-global-search.component.scss'],
    template: `
    <div class="sdc-search-content  {{ state }}">
      <div class="sdc-search-form">
        <div class="inp-border sdc-search-input">
          <input #searchInput class="input" type="text" [placeholder]="'Label.Search' | translate" autocomplete="off" />
        </div>
        <div class="sdc-search-type">
          @for (type of elementTypes; track type) {
            <nx-checkbox [id]="type" [checked]="selectedTypes[type]" (change)="toggleType(type)">
              {{ type | titlecase }}
            </nx-checkbox>
          }
        </div>
      </div>
      <div class="sdc-search-result sdc-scrollable">
        @for (item of data$ | async; track item.trackId) {
          @defer (on viewport) {
            <div class="sdc-search-result-item" (click)="goTo(item)">
              <div class="sdc-search-result-coverage">
                @if (item.coverage) {
                  <sdc-coverage-chart
                    [backgroundColor]="BACKGROUND_CHART_COLOR"
                    [coverage]="{ id: item.id, name: '', coverage: item.coverage }"
                    [size]="45"
                  />
                }
              </div>
              <div class=" sdc-cut-text sdc-search-result-item-name">{{ item.name }}</div>
              <div class="sdc-search-result-item-type"><sdc-tag [data]="{ name: item.type }" /></div>
            </div>
          } @placeholder {
            <div class="placeholder"></div>
          }
        }
      </div>
    </div>
  `,
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [SdcGlobalSearchService],
    imports: [CommonModule, NxCheckboxModule, SdcCoverageChartComponent, SdcTagComponent, TranslateModule]
})
export class SdcGlobalSearchComponent implements OnInit, OnDestroy {
  @ViewChild('searchInput', { static: true })
  private searchInput!: ElementRef;

  public BACKGROUND_CHART_COLOR = BACKGROUND_CHART_COLOR;
  public data$: Subject<ISummaryViewModel[]>;
  public elementTypes: SummaryViewType[] = Object.values(SummaryViewType);
  public selectedTypes: { [key in SummaryViewType]: boolean } = {
    COMPONENT: false,
    DEPARTMENT: false,
    SQUAD: false
  };

  private _state: OverlayItemStatus = 'closed';
  private subscription$: Array<Subscription> = [];

  constructor(private readonly globalSearchService: SdcGlobalSearchService) {
    this.data$ = this.globalSearchService.onDataChange();
  }

  public get state(): OverlayItemStatus {
    return this._state;
  }
  @Input()
  public set state(value: OverlayItemStatus) {
    this._state = value;
    setTimeout(() => this._state === 'open' && $('.sdc-search-input input')?.focus(), 300);
  }

  ngOnInit(): void {
    this.subscription$.push(this.searchBoxConfig());
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  public toggleType(type: SummaryViewType): void {
    this.selectedTypes[type] = !this.selectedTypes[type];

    this.searchData(this.searchInput.nativeElement.value, this.selectedTypes);
  }

  public goTo(item: ISummaryViewModel): void {
    this.globalSearchService.goTo(item);
  }

  private searchBoxConfig(): Subscription {
    return fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        distinctUntilChanged(),
        debounceTime(DEBOUNCE_TIME),
        map(() => {
          this.searchData(this.searchInput.nativeElement.value, this.selectedTypes);
        })
      )
      .subscribe();
  }

  private searchData(name: string, types: { [key in SummaryViewType]: boolean }): void {
    const sanitizedValue = this.sanetizeName(name);
    const validWords = sanitizedValue.split(' ').filter((word: string) => word.length > 2);
    const searchValue = validWords.length > 0 ? validWords.join(' ') : '';

    this.globalSearchService.getSummaryViews(searchValue, types);
  }

  private sanetizeName(name: string): string {
    return name.replace(/[^a-zA-Z0-9 ]/g, '');
  }
}
