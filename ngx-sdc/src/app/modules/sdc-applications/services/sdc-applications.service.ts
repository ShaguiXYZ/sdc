import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { componentsCoverage } from 'src/app/core/lib/sdc.utils';
import { IPageableModel, ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { SquadService } from 'src/app/core/services/sdc';
import { ApplicationsContextData, ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcApplicationsModel } from '../models';

@Injectable()
export class SdcApplicationsService implements OnDestroy {
  public contextData?: ApplicationsContextData;

  private subject$: Subject<SdcApplicationsModel>;

  constructor(private contextDataService: UiContextDataService, private squadService: SquadService) {
    this.subject$ = new Subject();

    this.contextData = this.contextDataService.getContextData(ContextDataInfo.APPLICATIONS_DATA);

    if (this.contextData?.squad !== null && this.contextData?.squad !== undefined) {
      this.squadData(this.contextData.squad, 0);
    }
  }

  public onDataChange(): Observable<SdcApplicationsModel> {
    return this.subject$.asObservable();
  }

  availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.squads();
  }

  squadData(squadId: number, page: number): void {
    this.squadService.squadComponents(squadId, page).then(pageable => {
      console.log('Components:', pageable);

      this.contextDataService.setContextData(
        ContextDataInfo.APPLICATIONS_DATA,
        { ...this.contextData, squad: squadId },
        { persistent: true }
      );

      this.subject$.next({
        squadId,
        coverage: componentsCoverage(pageable.page),
        components: pageable.page
      });
    });
  }

  public ngOnDestroy() {
    console.log('...Destroy test service...');
  }
}
