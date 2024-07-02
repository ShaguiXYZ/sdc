import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NxDropdownModule } from '@aposin/ng-aquila/dropdown';
import { NxFormfieldModule } from '@aposin/ng-aquila/formfield';
import { NxInputModule } from '@aposin/ng-aquila/input';
import { IPaginationTexts, NX_PAGINATION_TEXTS, NxPaginationModule } from '@aposin/ng-aquila/pagination';
import { NxRadioToggleModule } from '@aposin/ng-aquila/radio-toggle';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService, hasValue } from '@shagui/ng-shagui/core';
import { Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { IAppConfigurationModel, IComponentModel, IDepartmentModel, ISquadModel, ITagModel } from 'src/app/core/models/sdc';
import { SdcRouteService } from 'src/app/core/services/sdc';
import { SdcComplianceBarCardsComponent, SdcTagComponent } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { MetricStates } from 'src/app/shared/lib';
import { SdcApplicationsDataModel } from './models';
import { SdcApplicationsHomeService } from './services';

const myPaginationTexts: Partial<IPaginationTexts> = {
  ofLabel: 'of'
};

@Component({
  selector: 'sdc-applications-home',
  styleUrls: ['./sdc-applications-home.component.scss'],
  templateUrl: './sdc-applications-home.component.html',
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
    NxTooltipModule,
    ReactiveFormsModule,
    SdcComplianceBarCardsComponent,
    SdcTagComponent,
    TranslateModule
  ]
})
export class SdcApplicationsHomeComponent implements OnInit, OnDestroy {
  public elementsByPage!: number;
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public tags: { all: ITagModel[]; selected: ITagModel[]; availables: ITagModel[] } = { all: [], selected: [], availables: [] };
  public coverages: { key: string; label: string; style: string }[] = [];
  public applicationsInfo?: SdcApplicationsDataModel;

  @ViewChild('searchInput', { static: true })
  private searchInput!: ElementRef;
  private subscription$: Array<Subscription> = [];

  constructor(
    private readonly applicationsService: SdcApplicationsHomeService,
    private readonly contextDataService: ContextDataService,
    private readonly fb: FormBuilder,
    private readonly routerService: SdcRouteService
  ) {}

  ngOnInit(): void {
    this.elementsByPage = this.applicationsService.emementsByPage;

    this.subscription$.push(
      this.applicationsService.onDataChange().subscribe(info => {
        this.applicationsInfo = info;

        this.groupTags();

        this.contextDataService.set<IAppConfigurationModel>(ContextDataInfo.APP_CONFIG, {
          ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
          title: `Applications | ${this.applicationsInfo?.name ?? ''}`
        });
      }),
      this.searchBoxConfig()
    );

    this.createForm();
    this.initialize().then(() => this.applicationsService.loadData());
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  public componentClicked(component: IComponentModel): void {
    this.routerService.toComponent(component);
  }

  public departmentClicked(department: IDepartmentModel): void {
    this.routerService.toDepartment(department);
  }

  public squadClicked(squad: ISquadModel): void {
    this.routerService.toSquad(squad);
  }

  public squadChange(squad: number): void {
    !hasValue(squad) && this.form.controls['squadId'].setValue('');

    this.applicationsService.populateData({
      metricState: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad,
      tags: this.form.controls['tags'].value
    });
  }

  public coverageChange(metricState: MetricStates): void {
    !metricState && this.form.controls['coverage'].setValue('');

    this.applicationsService.populateData({
      metricState: metricState,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value,
      tags: this.form.controls['tags'].value
    });
  }

  public tagsChange(tags: string[]): void {
    !tags?.length && this.form.controls['tags'].setValue([]);

    this.applicationsService.populateData({
      metricState: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value,
      tags
    });
  }

  public resetFilter(): void {
    this.form.reset();

    this.applicationsService.populateData({
      metricState: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value,
      tags: this.form.controls['tags'].value
    });
  }

  public prevPage(): void {
    if (this.applicationsInfo) {
      this.applicationsInfo.paging.pageIndex--;

      this.applicationsService.populateData(
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

      this.applicationsService.populateData(
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

      this.applicationsService.populateData(
        {
          metricState: this.applicationsInfo.metricState,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId
        },
        this.applicationsInfo.paging.pageIndex
      );
    }
  }

  private async initialize() {
    const [coverages, squads, tags] = await Promise.all([
      this.applicationsService.availableCoverages(),
      this.applicationsService.availableSquads(),
      this.applicationsService.availableTags()
    ]);

    this.coverages = coverages;
    this.squads = squads.page;
    this.tags.all = tags.page;
    this.tags.all.sort((a, b) => a.name.localeCompare(b.name));
    this.groupTags();
  }

  private groupTags(): void {
    this.tags.selected = this.tags.all.filter(tag => this.applicationsInfo?.tags?.includes(tag.name)) ?? [];
    this.tags.availables = this.tags.all.filter(tag => !this.applicationsInfo?.tags?.includes(tag.name)) ?? [];
  }

  private createForm(): void {
    this.form = this.fb.group({
      coverage: [this.applicationsService.contextData?.filter?.metricState],
      name: [this.applicationsService.contextData?.filter?.name],
      squadId: [this.applicationsService.contextData?.filter?.squad],
      tags: [this.applicationsService.contextData?.filter?.tags]
    });
  }

  private searchBoxConfig(): Subscription {
    return fromEvent(this.searchInput.nativeElement, 'keyup')
      .pipe(
        map(event => event),
        debounceTime(DEBOUNCE_TIME),
        distinctUntilChanged()
      )
      .subscribe(() =>
        this.applicationsService.populateData(
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
