import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { IPaginationTexts, NX_PAGINATION_TEXTS } from '@aposin/ng-aquila/pagination';
import { Subscription, debounceTime, distinctUntilChanged, fromEvent, map } from 'rxjs';
import { DEBOUNCE_TIME } from 'src/app/core/constants';
import { hasValue } from 'src/app/core/lib';
import { ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { ELEMENTS_BY_PAGE } from 'src/app/core/services/http';
import { IComplianceModel } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcApplicationsDataModel } from './models';
import { SdcApplicationsService } from './services';

const myPaginationTexts: Partial<IPaginationTexts> = {
  ofLabel: 'of'
};

@Component({
  selector: 'sdc-applications',
  templateUrl: './sdc-applications.component.html',
  styleUrls: ['./sdc-applications.component.scss'],
  providers: [SdcApplicationsService, { provide: NX_PAGINATION_TEXTS, useValue: myPaginationTexts }]
})
export class SdcApplicationsComponent implements OnInit, OnDestroy {
  @ViewChild('searchInput', { static: true }) searchInput!: ElementRef;

  public ELEMENTS_BY_PAGE = ELEMENTS_BY_PAGE;
  public form!: FormGroup;
  public squads: ISquadModel[] = [];
  public coverages: { key: string; label: string; style: string }[] = [];
  public applicationsInfo?: SdcApplicationsDataModel;

  private subscription$: Array<Subscription> = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private contextDataService: UiContextDataService,
    private sdcApplicationsService: SdcApplicationsService
  ) {}

  ngOnInit(): void {
    this.subscription$.push(
      this.sdcApplicationsService.onDataChange().subscribe(info => {
        this.applicationsInfo = info;
      })
    );

    this.subscription$.push(this.searchBoxConfig());

    this.createForm();
    this.loadData();
  }

  ngOnDestroy(): void {
    this.subscription$.forEach(subscription => subscription.unsubscribe());
  }

  public complianceClicked(compliance: IComplianceModel): void {
    this.contextDataService.set(ContextDataInfo.METRICS_DATA, { compliance });
    this.router.navigate([AppUrls.metrics]);
  }

  public squadChange(squad: number): void {
    if (!hasValue(squad)) {
      this.form.controls['squadId'].setValue('');
    }

    this.sdcApplicationsService.populateData({
      coverage: this.form.controls['coverage'].value,
      name: this.form.controls['name'].value,
      squad
    });
  }

  public coverageChange(coverage: string): void {
    if (!coverage) {
      this.form.controls['coverage'].setValue('');
    }

    this.sdcApplicationsService.populateData({
      coverage,
      name: this.form.controls['name'].value,
      squad: this.form.controls['squadId'].value
    });
  }

  public prevPage(): void {
    if (this.applicationsInfo) {
      this.applicationsInfo.paging.pageIndex--;

      this.sdcApplicationsService.populateData(
        {
          coverage: this.applicationsInfo.coverage,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId
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
          coverage: this.applicationsInfo.coverage,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId
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
          coverage: this.applicationsInfo.coverage,
          name: this.applicationsInfo.name,
          squad: this.applicationsInfo.squadId
        },
        this.applicationsInfo.paging.pageIndex
      );
    }
  }

  private async loadData() {
    const [coverages, squads] = await Promise.all([
      this.sdcApplicationsService.availableCoverages(),
      this.sdcApplicationsService.availableSquads()
    ]);

    this.coverages = coverages;
    this.squads = squads.page;
  }

  private createForm(): void {
    this.form = this.fb.group({
      coverage: [this.sdcApplicationsService.contextData?.filter?.coverage],
      name: [this.sdcApplicationsService.contextData?.filter?.name],
      squadId: [this.sdcApplicationsService.contextData?.filter?.squad]
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
        this.sdcApplicationsService.populateData({
          coverage: this.form.controls['coverage'].value,
          name: this.form.controls['name'].value,
          squad: this.form.controls['squadId'].value
        })
      );
  }
}
