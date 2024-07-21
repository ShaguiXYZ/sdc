import { Injectable } from '@angular/core';
import { ContextDataService, HttpStatus, filterBy } from '@shagui/ng-shagui/core';
import { Observable, Subject } from 'rxjs';
import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { DepartmentService, SquadService } from 'src/app/core/services/sdc';
import { SdcOverlayService } from 'src/app/shared/components/sdc-overlay/services';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcDepartmentsContextData } from 'src/app/shared/models';
import { SdcDepartmentsDataModel } from '../models';

@Injectable()
export class SdcDepartmentsService {
  private contextData!: SdcDepartmentsContextData;
  private data$: Subject<Partial<SdcDepartmentsDataModel>> = new Subject();

  constructor(
    private contextDataService: ContextDataService,
    private departmentService: DepartmentService,
    private readonly overlayService: SdcOverlayService,
    private squadService: SquadService
  ) {
    this.contextData = this.contextDataService.get(ContextDataInfo.DEPARTMENTS_DATA);
  }

  public onDataChange(): Observable<Partial<SdcDepartmentsDataModel>> {
    return this.data$.asObservable();
  }

  public availableDepartments(filter?: string): void {
    this.departmentService
      .departments()
      .then(pageable => {
        const department = pageable.page.find(data => this.contextData?.department?.id === data.id);
        const departments = filter?.trim() ? filterBy(pageable.page, ['id', 'name'], filter) : pageable.page;

        this.contextData = { ...this.contextData, department, departmentFilter: filter };
        this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

        this.data$.next({ department, departments, departmentFilter: filter });
      })
      .catch(console.error);
  }

  public availableSquads(department: IDepartmentModel, filter?: string): void {
    this.squadService
      .squads()
      .then(pageable => {
        const squads: ISquadModel[] = pageable.page.filter(squad => squad.department.id === department.id);
        const squadsInView: ISquadModel[] = filter?.trim() ? filterBy(squads, ['id', 'name'], filter) : squads;

        this.contextData = { ...this.contextData, department, squadFilter: filter };
        this.contextDataService.set(ContextDataInfo.DEPARTMENTS_DATA, this.contextData, { persistent: true });

        this.data$.next({ department, squads, squadsInView, squadFilter: filter });
      })
      .catch(console.error);
  }

  public loadData(): void {
    this.availableDepartments(this.contextData?.departmentFilter);

    if (this.contextData?.department) {
      this.availableSquads(this.contextData.department, this.contextData.squadFilter);
    }
  }

  public updateRemoteDepartments = (): void => {
    this.departmentService
      .updateDeparments({
        [HttpStatus.unauthorized]: this.onUnauthorizedError,
        [HttpStatus.forbidden]: this.onUnauthorizedError,
        [HttpStatus.locked]: this.onLockedError
      })
      .then(departments => {
        console.log('Departments updated', departments);

        this.availableDepartments(this.contextData?.departmentFilter);
      })
      .catch(console.error);
  };

  private onUnauthorizedError = (): void => {
    this.overlayService.toggleLogin();
  };

  private onLockedError = (error: unknown): void => {
    console.log('onLockedError', error);
  };
}
