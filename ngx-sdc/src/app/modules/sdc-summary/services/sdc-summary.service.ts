import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { ComponentService, SquadService } from 'src/app/core/services/sdc';
import { SummaryContextData } from 'src/app/modules/sdc-summary/models/sdc-summary-context-data.model';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcSummaryDataModel } from '../models';

@Injectable()
export class SdcSummaryService {
  private contextData!: SummaryContextData;
  private summary$: Subject<SdcSummaryDataModel>;
  private data!: SdcSummaryDataModel;

  constructor(
    private contextDataService: UiContextDataService,
    private componetService: ComponentService,
    private squadService: SquadService
  ) {
    this.summary$ = new Subject();
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.SUMMARY_DATA);
    this.data = { filter: this.contextData?.filter, squad: this.contextData?.squad, components: [], squads: [] };

    this.summary$.next(this.data);
  }

  public onSummaryChange(): Observable<SdcSummaryDataModel> {
    return this.summary$.asObservable();
  }

  public availableSquads(filter?: string): void {
    this.squadService.squads().then(pageable => {
      let squads: ISquadModel[] = [];

      if (filter?.trim().length) {
        squads = pageable.page.filter(squad => squad.name.toLocaleLowerCase().includes(filter.toLocaleLowerCase()));
      } else {
        squads = pageable.page;
      }

      this.data = { ...this.data, squads, filter };
      this.contextData = { ...this.contextData, filter };
      this.contextDataService.setContextData(ContextDataInfo.SUMMARY_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public selectedSquad(squad: ISquadModel): void {
    this.componetService.filter(undefined, squad.id).then(pageable => {
      this.data = { ...this.data, squad, components: pageable.page };
      this.contextData = { ...this.contextData, squad };
      this.contextDataService.setContextData(ContextDataInfo.SUMMARY_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public summaryData(): void {
    this.availableSquads(this.contextData?.filter);

    if (this.contextData?.squad) {
      this.selectedSquad(this.contextData.squad);
    }
  }
}
