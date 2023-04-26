import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { filterByProperties } from 'src/app/core/lib';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcDepartmentsContextData, SdcDepartmentsDataModel } from '../models';

@Injectable()
export class SdcDepartmentsService {
  private contextData!: SdcDepartmentsContextData;
  private summary$: Subject<SdcDepartmentsDataModel>;
  private data!: SdcDepartmentsDataModel;

  constructor(
    private contextDataService: UiContextDataService,
    private departmentService: DepartmentService,
    private squadService: SquadService
  ) {
    this.summary$ = new Subject();
    this.contextData = this.contextDataService.get(ContextDataInfo.DEPARTMENTS_DATA);
    this.data = {
      departmentFilter: this.contextData?.departmentFilter,
      squadFilter: this.contextData?.squadFilter,
      department: this.contextData?.department,
      squads: [],
      squadsInView: [],
      departments: []
    };

    this.summary$.next(this.data);
  }

  public onDataChange(): Observable<SdcDepartmentsDataModel> {
    return this.summary$.asObservable();
  }

  public availableDepartments(filter?: string): void {
    this.departmentService.departments().then(pageable => {
      let departments: IDepartmentModel[] = [];

      if (filter?.trim().length) {
        departments = filterByProperties(pageable.page, ['id', 'name'], filter);
      } else {
        departments = pageable.page;
      }

      this.data = { ...this.data, departments, departmentFilter: filter };
      this.contextData = { ...this.contextData, departmentFilter: filter };
      this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public availableSquads(department: IDepartmentModel, filter?: string): void {
    this.squadService.squads().then(pageable => {
      const squads: ISquadModel[] = pageable.page.filter(squad => squad.department.id === department.id);
      const squadsInView: ISquadModel[] = filter?.trim().length ? filterByProperties(squads, ['id', 'name'], filter) : squads;

      this.data = { ...this.data, department, squads, squadsInView, squadFilter: filter };
      this.contextData = { ...this.contextData, department, squadFilter: filter };
      this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public loadData(): void {
    this.availableDepartments(this.contextData?.departmentFilter);

    if (this.contextData?.department) {
      this.availableSquads(this.contextData.department, this.contextData.squadFilter);
    }
  }
}
