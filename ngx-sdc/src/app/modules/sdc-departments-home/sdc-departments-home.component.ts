import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ICoverageModel, IDepartmentModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { IStateCount } from 'src/app/shared/components';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { ApplicationsContextData } from '../sdc-applications';
import { SdcDepartmentsDataModel } from './models';
import { SdcDepartmentsService } from './services';

@Component({
  selector: 'sdc-departments-home',
  templateUrl: './sdc-departments-home.component.html',
  styleUrls: ['./sdc-departments-home.component.scss'],
  providers: [SdcDepartmentsService]
})
export class SdcDepartmentsHomeComponent implements OnInit, OnDestroy {
  public departmentsData?: SdcDepartmentsDataModel;

  private summary$!: Subscription;

  constructor(private sdcDepartmentService: SdcDepartmentsService) {}

  ngOnInit(): void {
    this.summary$ = this.sdcDepartmentService.onDataChange().subscribe(data => {
      this.departmentsData = data;
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
    this.squadsByDepartmernt(event as IDepartmentModel, this.departmentsData?.squadFilter);
  }

  private squadsByDepartmernt(department?: IDepartmentModel, filter?: string) {
    if (department) {
      this.sdcDepartmentService.availableSquads(department, filter);
    }
  }
}
