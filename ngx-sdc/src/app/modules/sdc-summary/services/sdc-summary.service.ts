import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { DataInfo } from 'src/app/core/interfaces/dataInfo';
import { ISquadModel } from 'src/app/core/models/sdc';
import { ISdcSessionData } from 'src/app/core/models/session/session.model';
import { UiContextDataService } from 'src/app/core/services';
import { SquadService } from 'src/app/core/services/sdc';
import { ContextDataNames } from 'src/app/shared/config/context-info';

@Injectable()
export class SdcSummaryService {
  private summary$: Subject<DataInfo>;
  private data!: DataInfo;

  constructor(private contextData: UiContextDataService, private squadService: SquadService) {
    const sessionData: ISdcSessionData = this.contextData.getContextData(ContextDataNames.sdcSessionData);

    this.summary$ = new Subject();
    this.summary(sessionData.squad.id);
  }

  public onSummaryChange(): Observable<DataInfo> {
    return this.summary$.asObservable();
  }

  private summary(squadId: number): void {
    this.data = {};

    this.squadService.squad(squadId).then(squad => {
      this.data['squad'] = squad;
      this.components(squad);
      this.squads();
    });
  }

  private components(squad: ISquadModel): void {
    this.squadService.squadComponents(squad.id).then(pageable => {
      this.data['components'] = pageable.page;
      this.data['components-in-view'] = pageable.page.slice(0,3);
      this.summary$.next(this.data);
    });
  }

  private squads(): void {
    this.squadService.squads().then(pageable => {
      this.data['squads'] = pageable.page;
      this.summary$.next(this.data);
    });
  }
}
