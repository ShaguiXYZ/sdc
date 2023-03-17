import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ISdcSessionData } from 'src/app/core/models/session/session.model';
import { SquadService, UiAppContextDataService } from 'src/app/core/services';
import { ContextDataNames } from 'src/app/shared/config/context-info';
import { SdcSummaryModel } from '../models/sdc-summary.model';

@Injectable()
export class SdcSummaryService {
  private sdcSummary!: SdcSummaryModel;
  private subject$: Subject<SdcSummaryModel>;

  constructor(private contextData: UiAppContextDataService, private squadService: SquadService) {
    this.subject$ = new Subject();
    const sessionData: ISdcSessionData = this.contextData.getContextData(ContextDataNames.sdcSessionData);

    this.sdcSummary = { squad: sessionData.squad };

    this.squadService.squadState(sessionData.squad.id).then(analysis => {
      this.sdcSummary.squadCoverage = analysis.coverage;

      console.log(this.sdcSummary);

      this.subject$.next(this.sdcSummary);
    });
  }

  public onDataChange(): Observable<SdcSummaryModel> {
    return this.subject$.asObservable();
  }
}
