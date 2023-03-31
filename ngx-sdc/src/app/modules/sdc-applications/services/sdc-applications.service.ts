import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { componentsCoverage } from 'src/app/core/lib/sdc.utils';
import { IPageableModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { SquadService } from 'src/app/core/services/sdc';
import { ApplicationsContextData, ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcApplicationsModel } from '../models';
import { IComplianceModel } from 'src/app/shared/components';
import { ELEMENTS_BY_PAGE } from 'src/app/core/constants/app.constants';

@Injectable()
export class SdcApplicationsService implements OnDestroy {
  public contextData?: ApplicationsContextData;

  private subject$: Subject<SdcApplicationsModel>;

  constructor(private contextDataService: UiContextDataService, private squadService: SquadService) {
    this.subject$ = new Subject();

    this.contextData = this.contextDataService.getContextData(ContextDataInfo.APPLICATIONS_DATA);

    if (this.contextData?.squad !== null && this.contextData?.squad !== undefined) {
      this.squadData(this.contextData.squad, this.contextData?.page || 0, ELEMENTS_BY_PAGE);
    }
  }

  public onDataChange(): Observable<SdcApplicationsModel> {
    return this.subject$.asObservable();
  }

  public availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.squads();
  }

  public squadData(squadId: number, page: number, ps: number): void {
    this.squadService.squadComponents(squadId, page, ps).then(pageable => {
      console.log('Components:', pageable);

      this.contextDataService.setContextData(
        ContextDataInfo.APPLICATIONS_DATA,
        { ...this.contextData, squad: squadId, page },
        { persistent: true }
      );

      this.subject$.next({
        squadId,
        coverage: componentsCoverage(pageable.page),
        compliances: pageable.page.map(IComplianceModel.fromComponentModel),
        paging: pageable.paging
      });
    });
  }

  public ngOnDestroy() {
    console.log('...Destroy test service...');
  }
}
