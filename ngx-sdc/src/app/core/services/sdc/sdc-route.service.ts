import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ContextDataService } from '@shagui/ng-shagui/core';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { ApplicationsContextData, SdcDepartmentsContextData, SdcMetricsContextData, SdcSquadsContextData } from 'src/app/shared/models';
import { IComponentModel, IDepartmentModel, ISquadModel } from '../../models/sdc';
import { ComponentService } from './component.service';
import { DepartmentService } from './department.service';
import { SquadService } from './squad.service';

@Injectable({ providedIn: 'root' })
export class SdcRouteService {
  private readonly contextDataService = inject(ContextDataService);

  constructor(
    private readonly componentService: ComponentService,
    private readonly squadService: SquadService,
    private readonly departmentService: DepartmentService,
    private readonly router: Router
  ) {}

  public toComponentById = (componentId: number): void => {
    this.componentService.component(componentId).then(this.toComponent);
  };

  public toDepartmentById = (departmentId: number): void => {
    this.departmentService.department(departmentId).then(this.toDepartment);
  };

  public toSquadById = (squadId: number): void => {
    this.squadService.squad(squadId).then(this.toSquad);
  };

  public toApplications = (applications: Partial<ApplicationsContextData>): void => {
    const contextData: Partial<ApplicationsContextData> = { ...applications };

    this.navigateTo(AppUrls.applications, ContextDataInfo.APPLICATIONS_DATA, contextData);
  };

  public toComponent = (component: IComponentModel): void => {
    const contextData: SdcMetricsContextData = { component };

    this.navigateTo(AppUrls.metrics, ContextDataInfo.METRICS_DATA, contextData);
  };

  public toDepartment = (department: IDepartmentModel): void => {
    const contextData: SdcDepartmentsContextData = { department };

    this.navigateTo(AppUrls.departments, ContextDataInfo.DEPARTMENTS_DATA, contextData);
  };

  public toSquad = (squad: ISquadModel): void => {
    const contextData: SdcSquadsContextData = { squad };

    this.navigateTo(AppUrls.squads, ContextDataInfo.SQUADS_DATA, contextData);
  };

  private navigateTo = <T = unknown>(url: string, contextName: ContextDataInfo, contextData: T): void => {
    this.router.navigateByUrl(`/${AppUrls.routing}`, { skipLocationChange: true }).then(() => {
      this.contextDataService.set(contextName, contextData);
      this.router.navigate([url]);
    });
  };
}
