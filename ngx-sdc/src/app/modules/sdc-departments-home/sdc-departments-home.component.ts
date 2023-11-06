import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ICoverageModel, IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcSquadsContextData } from '../sdc-squads-home';
import { SdcDepartmentsDataModel } from './models';
import { SdcDepartmentsService } from './services';

@Component({
  selector: 'sdc-departments-home',
  templateUrl: './sdc-departments-home.component.html',
  styleUrls: ['./sdc-departments-home.component.scss'],
  providers: [SdcDepartmentsService]
})
export class SdcDepartmentsHomeComponent implements OnInit, OnDestroy {
  public departmentsData!: SdcDepartmentsDataModel;

  private summary$!: Subscription;

  constructor(
    private readonly router: Router,
    private readonly contextDataService: UiContextDataService,
    private readonly sdcDepartmentService: SdcDepartmentsService
  ) {}

  ngOnInit(): void {
    this.summary$ = this.sdcDepartmentService.onDataChange().subscribe(data => {
      this.departmentsData = { ...this.departmentsData, ...data };

      this.contextDataService.set(ContextDataInfo.APP_CONFIG, {
        ...this.contextDataService.get(ContextDataInfo.APP_CONFIG),
        title: `Departments | ${this.departmentsData?.department?.name ?? ''}`
      });
    });

    this.sdcDepartmentService.loadData();
  }

  ngOnDestroy(): void {
    this.summary$.unsubscribe();
  }

  public onSearchDepartmentChanged(filter: string): void {
    if (this.departmentsData) {
      this.sdcDepartmentService.availableDepartments(filter);
    }
  }

  public onSearchSquadChanged(filter: string): void {
    this.squadsByDepartmernt(this.departmentsData?.department, filter);
  }

  public onClickDepartment(event: ICoverageModel) {
    this.squadsByDepartmernt(event, this.departmentsData?.squadFilter);
  }

  public onClickSquad(squad: ICoverageModel) {
    const squadContextData: SdcSquadsContextData = { squad: squad as ISquadModel };
    this.contextDataService.set(ContextDataInfo.SQUADS_DATA, squadContextData);
    this.router.navigate([AppUrls.squads]);
  }

  private squadsByDepartmernt(department?: IDepartmentModel, filter?: string) {
    if (department) {
      this.sdcDepartmentService.availableSquads(department, filter);
    }
  }
}
