import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console, filterByProperties } from 'src/app/core/lib';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcDepartmentsContextData, SdcDepartmentsDataModel } from '../models';

@Injectable()
export class SdcDepartmentsService {
  private contextData!: SdcDepartmentsContextData;
  private data$: Subject<Partial<SdcDepartmentsDataModel>>;

  constructor(
    private contextDataService: UiContextDataService,
    private departmentService: DepartmentService,
    private squadService: SquadService
  ) {
    this.data$ = new Subject();
    this.contextData = this.contextDataService.get(ContextDataInfo.DEPARTMENTS_DATA);

    this.data$.next({
      departmentFilter: this.contextData?.departmentFilter,
      squadFilter: this.contextData?.squadFilter,
      department: this.contextData?.department
    });
  }

  public onDataChange(): Observable<Partial<SdcDepartmentsDataModel>> {
    return this.data$.asObservable();
  }

  public availableDepartments(filter?: string): void {
    this.departmentService
      .departments()
      .then(pageable => {
        let departments: IDepartmentModel[] = [];
        const department = pageable.page.find(data => this.contextData?.department?.id === data.id);

        if (filter?.trim().length) {
          departments = filterByProperties(pageable.page, ['id', 'name'], filter);
        } else {
          departments = pageable.page;
        }

        this.contextData = { ...this.contextData, department, departmentFilter: filter };
        this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

        this.data$.next({ department, departments, departmentFilter: filter });
      })
      .catch(_console.error);
  }

  public availableSquads(department: IDepartmentModel, filter?: string): void {
    this.squadService
      .squads()
      .then(pageable => {
        const squads: ISquadModel[] = pageable.page.filter(squad => squad.department.id === department.id);
        const squadsInView: ISquadModel[] = filter?.trim().length ? filterByProperties(squads, ['id', 'name'], filter) : squads;

        this.contextData = { ...this.contextData, department, squadFilter: filter };
        this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

        this.data$.next({ department, squads, squadsInView, squadFilter: filter });
      })
      .catch(_console.error);
  }

  public loadData(): void {
    this.availableDepartments(this.contextData?.departmentFilter);

    if (this.contextData?.department) {
      this.availableSquads(this.contextData.department, this.contextData.squadFilter);
    }
  }
}
