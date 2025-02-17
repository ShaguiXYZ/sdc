import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NxHeadlineModule } from '@aposin/ng-aquila/headline';
import { NxLinkModule } from '@aposin/ng-aquila/link';
import { NxTooltipModule } from '@aposin/ng-aquila/tooltip';
import { TranslateModule } from '@ngx-translate/core';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { Subscription } from 'rxjs';
import { AlertService } from 'src/app/core/components';
import { AppConfigurationModel, ICoverageModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { IfRoleDirective } from 'src/app/core/services';
import { SdcRouteService } from 'src/app/core/services/sdc';
import { SdcCoveragesComponent } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcDepartmentSummaryComponent } from './components';
import { SdcDepartmentsDataModel } from './models';
import { SdcDepartmentsService } from './services';

@Component({
  selector: 'sdc-departments-home',
  styleUrls: ['./sdc-departments-home.component.scss'],
  templateUrl: './sdc-departments-home.component.html',
  providers: [SdcDepartmentsService],
  imports: [
    CommonModule,
    IfRoleDirective,
    SdcCoveragesComponent,
    SdcDepartmentSummaryComponent,
    NxHeadlineModule,
    NxLinkModule,
    NxTooltipModule,
    TranslateModule
  ]
})
export class SdcDepartmentsHomeComponent implements OnInit, OnDestroy {
  public departmentsData!: SdcDepartmentsDataModel;

  private summary$!: Subscription;

  constructor(
    private readonly alertService: AlertService,
    private readonly contextDataService: ContextDataService,
    private readonly departmentService: SdcDepartmentsService,
    private readonly routerService: SdcRouteService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.departmentService.onDataChange().subscribe(data => {
      this.departmentsData = { ...this.departmentsData, ...data };

      const appConfig = this.contextDataService.get<AppConfigurationModel>(ContextDataInfo.APP_CONFIG);

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...appConfig,
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

  public onClickDepartment(event: ICoverageModel): void {
    this.squadsByDepartmernt(event, this.departmentsData?.squadFilter);
  }

  public onClickSquad(squad: ICoverageModel): void {
    this.routerService.toSquad(squad as ISquadModel);
  }

  public onReloadDepartments(): void {
    this.alertService.confirm(
      {
        title: 'Alerts.UpdateDepartments.Title',
        text: 'Alerts.UpdateDepartments.Description'
      },
      this.departmentService.updateRemoteDepartments,
      { okText: 'Label.Yes', cancelText: 'Label.No' }
    );
  }

  private squadsByDepartmernt(department?: IDepartmentModel, filter?: string): void {
    if (department) {
      this.departmentService.availableSquads(department, filter);
    }
  }
}
