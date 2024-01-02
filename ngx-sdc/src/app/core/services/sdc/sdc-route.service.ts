import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { ApplicationsContextData } from 'src/app/shared/models';
import { IComponentModel, IDepartmentModel, ISquadModel } from '../../models/sdc';
import { ContextDataService } from '../context-data';
import { ComponentService } from './component.service';
import { DepartmentService } from './department.service';
import { SquadService } from './squad.service';

@Injectable({ providedIn: 'root' })
export class SdcRouteService {
  constructor(
    private readonly contextDataService: ContextDataService,
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
    this.contextDataService.set(ContextDataInfo.APPLICATIONS_DATA, applications);
    this.router.navigate([AppUrls.applications]);
  };

  public toComponent = (component: IComponentModel): void => {
    this.navigateTo(AppUrls.metrics, ContextDataInfo.METRICS_DATA, { component });
  };

  public toDepartment = (department: IDepartmentModel): void => {
    this.navigateTo(AppUrls.departments, ContextDataInfo.DEPARTMENTS_DATA, { department });
  };

  public toSquad = (squad: ISquadModel): void => {
    this.navigateTo(AppUrls.squads, ContextDataInfo.SQUADS_DATA, { squad });
  };

  private navigateTo = (url: string, contextName: ContextDataInfo, contextData: any): void => {
    this.router.navigateByUrl(`/${AppUrls.routing}`, { skipLocationChange: true }).then(() => {
      this.contextDataService.set(contextName, contextData);
      this.router.navigate([url]);
    });
  };
}
