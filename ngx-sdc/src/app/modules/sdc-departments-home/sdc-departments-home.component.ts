import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { TranslateModule } from '@ngx-translate/core';
import { Subscription } from 'rxjs';
import { ICoverageModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { SdcRouteService } from 'src/app/core/services/sdc';
import { SdcComplianceBarCardsComponent, SdcCoveragesComponent } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcDepartmentSummaryComponent } from './components';
import { SdcDepartmentsDataModel } from './models';
import { SdcDepartmentsService } from './services';

@Component({
  selector: 'sdc-departments-home',
  styleUrls: ['./sdc-departments-home.component.scss'],
  templateUrl: './sdc-departments-home.component.html',
  providers: [SdcDepartmentsService],
  standalone: true,
  imports: [
    SdcComplianceBarCardsComponent,
    SdcCoveragesComponent,
    SdcDepartmentSummaryComponent,
    CommonModule,
    NxHeadlineModule,
    NxLinkModule,
    TranslateModule
  ]
})
export class SdcDepartmentsHomeComponent implements OnInit, OnDestroy {
  public departmentsData!: SdcDepartmentsDataModel;

  private summary$!: Subscription;

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly departmentService: SdcDepartmentsService,
    private readonly routerService: SdcRouteService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.departmentService.onDataChange().subscribe(data => {
      this.departmentsData = { ...this.departmentsData, ...data };

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
        title: `Departments | ${this.departmentsData?.department?.name ?? ''}`
      });
    });

    this.departmentService.loadData();
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public onSearchDepartmentChanged(filter: string): void {
    if (this.departmentsData) {
      this.departmentService.availableDepartments(filter);
    }
  }

  public onSearchSquadChanged(filter: string): void {
    this.squadsByDepartmernt(this.departmentsData?.department, filter);
  }

  public onClickDepartment(event: ICoverageModel) {
    this.squadsByDepartmernt(event, this.departmentsData?.squadFilter);
  }

  public onClickSquad(squad: ICoverageModel) {
    this.routerService.toSquad(squad as ISquadModel);
  }

  private squadsByDepartmernt(department?: IDepartmentModel, filter?: string) {
    if (department) {
      this.departmentService.availableSquads(department, filter);
    }
  }
}
