import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ELEMENTS_BY_PAGE } from 'src/app/core/constants/app.constants';
import { IPageableModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { SquadService } from 'src/app/core/services/sdc';
import { ComponentService } from 'src/app/core/services/sdc/component.services';
import { IComplianceModel } from 'src/app/shared/components';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { ApplicationsContextData, ApplicationsFilter, SdcApplicationsDataModel } from '../models';
import { ISdcSessionData } from 'src/app/core/models/session/session.model';

@Injectable()
export class SdcApplicationsService implements OnDestroy {
  public contextData?: ApplicationsContextData;

  private sessionData!: ISdcSessionData;
  private subject$: Subject<SdcApplicationsDataModel>;

  constructor(
    private contextDataService: UiContextDataService,
    private componetService: ComponentService,
    private squadService: SquadService
  ) {
    this.subject$ = new Subject();
    this.sessionData = this.contextDataService.getContextData(ContextDataInfo.SDC_SESSION_DATA);
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.APPLICATIONS_DATA);
    this.squadData(
      this.contextData?.filter?.name,
      this.contextData?.filter?.squad || this.sessionData.squad.id,
      this.contextData?.page || 0,
      ELEMENTS_BY_PAGE
    );
  }

  public populateData(filter: ApplicationsFilter, page?: number) {
    this.squadData(filter.name, filter.squad, page || 0, ELEMENTS_BY_PAGE);
  }

  public onDataChange(): Observable<SdcApplicationsDataModel> {
    return this.subject$.asObservable();
  }

  public availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.squads();
  }

  private squadData(name?: string, squadId?: number, page?: number, ps?: number): void {
    this.componetService.filter(name, squadId, page, ps).then(pageable => {
      this.contextDataService.setContextData(
        ContextDataInfo.APPLICATIONS_DATA,
        { ...this.contextData, filter: { name, squad: squadId }, page },
        { persistent: true }
      );

      this.subject$.next({
        squadId,
        name,
        // coverage: componentsCoverage(pageable.page),
        compliances: pageable.page.map(IComplianceModel.fromComponentModel),
        paging: pageable.paging
      });
    });
  }

  public ngOnDestroy() {
    console.log('...Destroy test service...');
  }
}
