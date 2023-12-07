import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { IPaginationTexts, NX_PAGINATION_TEXTS, NxPaginationModule } from '@aposin/ng-aquila/pagination';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { hasValue } from 'src/app/core/lib';
import { IComponentModel, IDepartmentModel, ISquadModel, ITagModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { SdcComplianceBarCardsComponent, SdcTagComponent } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo, ELEMENTS_BY_PAGE } from 'src/app/shared/constants';
import { MetricStates } from 'src/app/shared/lib';
import { SdcApplicationsDataModel } from './models';
import { SdcApplicationsHomeService } from './services';

const myPaginationTexts: Partial<IPaginationTexts> = {
  ofLabel: 'of'
};

@Component({
  selector: 'sdc-applications-home',
  templateUrl: './sdc-applications-home.component.html',
  styleUrls: ['./sdc-applications-home.component.scss'],
  providers: [SdcApplicationsHomeService, { provide: NX_PAGINATION_TEXTS, useValue: myPaginationTexts }],
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    NxDropdownModule,
    NxFormfieldModule,
    NxInputModule,
    NxPaginationModule,
    NxRadioToggleModule,
    ReactiveFormsModule,
    SdcComplianceBarCardsComponent,
    SdcTagComponent,
    TranslateModule
  ]
})
export class SdcApplicationsHomeComponent implements OnInit, OnDestroy {
  @ViewChild('searchInput', { static: true }) searchInput!: ElementRef;

  public ELEMENTS_BY_PAGE = ELEMENTS_BY_PAGE;
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public tags: { all: ITagModel[]; selected: ITagModel[]; availables: ITagModel[] } = { all: [], selected: [], availables: [] };

  public coverages: { key: string; label: string; style: string }[] = [];
  public applicationsInfo?: SdcApplicationsDataModel;

  private subscription$: Array<Subscription> = [];

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly sdcApplicationsService: SdcApplicationsHomeService
  ) {}

  ngOnInit(): void {
    this.subscription$.push(
      this.sdcApplicationsService.onDataChange().subscribe(info => {
        this.applicationsInfo = info;

        this.groupTags();

        this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
          ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
          title: `Applications | ${this.applicationsInfo?.name ?? ''}`
        });
      })
    );

    this.subscription$.push(this.searchBoxConfig());

    this.createForm();
    this.loadData();
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  public complianceClicked(component: IComponentModel): void {
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, { component });
    this.router.navigate([AppUrls.metrics]);
  }

  public departmentClicked(department: IDepartmentModel): void {
    this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, { department });
    this.router.navigate([AppUrls.departments]);
  }

  public squadClicked(squad: ISquadModel): void {
    this.contextDataService.set(ContextDataInfo.SQUADS_DATA, { squad });
    this.router.navigate([AppUrls.squads]);
  }

  public squadChange(squad: number): void {
    if (!hasValue(squad)) {
      this.form.controls['squadId'].setValue('');
    }

    this.sdcApplicationsService.populateData({
      metricState: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad,
      tags: this.form.controls['tags'].value
    });
  }

  public coverageChange(metricState: MetricStates): void {
    if (!metricState) {
      this.form.controls['coverage'].setValue('');
    }

    this.sdcApplicationsService.populateData({
      metricState: metricState,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value,
      tags: this.form.controls['tags'].value
    });
  }

  public tagsChange(tags: string[]): void {
    if (!tags?.length) {
      this.form.controls['tags'].setValue([]);
    }

    this.sdcApplicationsService.populateData({
      metricState: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value,
      tags
    });
  }

  public prevPage(): void {
    if (this.applicationsInfo) {
      this.applicationsInfo.paging.pageIndex--;

      this.sdcApplicationsService.populateData(
        {
          metricState: this.applicationsInfo.metricState,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId,
          tags: this.applicationsInfo.tags
        },
        this.applicationsInfo.paging.pageIndex
      );
    }
  }

  public nextPage(): void {
    if (this.applicationsInfo) {
      this.applicationsInfo.paging.pageIndex++;

      this.sdcApplicationsService.populateData(
        {
          metricState: this.applicationsInfo.metricState,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId,
          tags: this.applicationsInfo.tags
        },
        this.applicationsInfo.paging.pageIndex
      );
    }
  }

  public goToPage(n: number): void {
    if (this.applicationsInfo) {
      this.applicationsInfo.paging.pageIndex = n - 1;

      this.sdcApplicationsService.populateData(
        {
          metricState: this.applicationsInfo.metricState,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId
        },
        this.applicationsInfo.paging.pageIndex
      );
    }
  }

  private groupTags(): void {
    this.tags.selected = this.tags.all.filter(tag => this.applicationsInfo?.tags?.includes(tag.name)) ?? [];
    this.tags.availables = this.tags.all.filter(tag => !this.applicationsInfo?.tags?.includes(tag.name)) ?? [];
  }

  private async loadData() {
    const [coverages, squads, tags] = await Promise.all([
      this.sdcApplicationsService.availableCoverages(),
      this.sdcApplicationsService.availableSquads(),
      this.sdcApplicationsService.availableTags()
    ]);

    this.coverages = coverages;
    this.squads = squads.page;
    this.tags.all = tags.page;
    this.tags.all.sort((a, b) => a.name.localeCompare(b.name));
    this.groupTags();
  }

  private createForm(): void {
    this.form = this.fb.group({
      coverage: [this.sdcApplicationsService.contextData?.filter?.metricState],
      name: [this.sdcApplicationsService.contextData?.filter?.name],
      squadId: [this.sdcApplicationsService.contextData?.filter?.squad],
      tags: [this.sdcApplicationsService.contextData?.filter?.tags]
    });
  }

  private searchBoxConfig(): Subscription {
    return fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        map(event => event),
        distinctUntilChanged(),
        debounceTime(DEBOUNCE_TIME)
      )
      .subscribe(() =>
        this.sdcApplicationsService.populateData(
          {
            metricState: this.form.controls['coverage'].value,
            name: this.form.controls['name'].value,
            squad: this.form.controls['squadId'].value
          },
          undefined,
          false
        )
      );
  }
}
