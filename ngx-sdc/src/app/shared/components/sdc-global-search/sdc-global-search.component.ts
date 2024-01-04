import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { Subject, Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { ISummaryViewModel, SummaryViewType } from 'src/app/core/models/sdc';
import { SdcTagComponent } from '../sdc-tag';
import { SdcGlobalSearchService } from './services';
import { SdcCoverageChartComponent } from '../sdc-charts';

@Component({
  selector: 'sdc-global-search',
  styleUrls: ['./sdc-global-search.component.scss'],
  template: `
    <div class="sdc-search-content">
      <div class="sdc-search-form">
        <div class="inp-border sdc-search-input">
          <input #searchInput class="input" type="text" [placeholder]="'Label.Search' | translate" autocomplete="off" />
        </div>
        <div class="sdc-search-type">
          @for (type of elementTypes; track type) {
            <label class="label-type" [for]="type">
              {{ type | titlecase }}
              <input type="checkbox" [id]="type" (change)="toggleType(type)" />
            </label>
          }
        </div>
      </div>
      <div class="sdc-search-result sdc-scrollable">
        @for (item of data$ | async; track item.trackId) {
          @defer (on viewport) {
            <div class="sdc-search-result-item" (click)="goTo(item)">
              <div class="sdc-search-result-coverage">
                @if (item.coverage) {
                  <sdc-coverage-chart [size]="45" [coverage]="{ id: item.id, name: '', coverage: item.coverage }" />
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
  providers: [SdcGlobalSearchService],
  standalone: true,
  imports: [CommonModule, SdcCoverageChartComponent, SdcTagComponent, TranslateModule]
})
export class SdcGlobalSearchComponent implements OnInit, OnDestroy {
  public data$: Subject<ISummaryViewModel[]>;
  public elementTypes: SummaryViewType[] = [];

  private selectedTypes: SummaryViewType[] = [];

  @ViewChild('searchInput', { static: true })
  private searchInput!: ElementRef;
  private subscription$: Array<Subscription> = [];

  constructor(private readonly globalSearchService: SdcGlobalSearchService) {
    this.elementTypes = Object.values(SummaryViewType);
    this.data$ = this.globalSearchService.onDataChange();
  }

  ngOnInit(): void {
    this.subscription$.push(this.searchBoxConfig());
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  public toggleType(type: SummaryViewType): void {
    if (this.selectedTypes.includes(type)) {
      this.selectedTypes = this.selectedTypes.filter(selectedType => selectedType !== type);
    } else {
      this.selectedTypes.push(type);
    }

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

  private searchData(name: string, types: SummaryViewType[]): void {
    const sanitizedValue = this.sanetizeName(this.searchInput.nativeElement.value);
    const validWords = sanitizedValue.split(' ').filter((word: string) => word.length > 2);
    const searchValue = validWords.length > 0 ? validWords.join(' ') : '';

    this.globalSearchService.getSummaryViews(searchValue, this.selectedTypes);
  }

  private sanetizeName(name: string): string {
    return name.replace(/[^a-zA-Z0-9 ]/g, '');
  }
}
