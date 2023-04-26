import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';
import { UiContextDataService } from 'src/app/core/services';
import { ComponentService, SquadService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants/context-data';
import { SdcSquadsDataModel, SdcSquadsContextData } from '../models';
import { filterByProperty } from 'src/app/core/lib';

@Injectable()
export class SdcSquadsService {
  private contextData!: SdcSquadsContextData;
  private summary$: Subject<SdcSquadsDataModel>;
  private data!: SdcSquadsDataModel;

  constructor(
    private contextDataService: UiContextDataService,
    private componetService: ComponentService,
    private squadService: SquadService
  ) {
    this.summary$ = new Subject();
    this.contextData = this.contextDataService.getContextData(ContextDataInfo.SQUADS_DATA);
    this.data = { filter: this.contextData?.filter, squad: this.contextData?.squad, components: [], squads: [] };

    this.summary$.next(this.data);
  }

  public onDataChange(): Observable<SdcSquadsDataModel> {
    return this.summary$.asObservable();
  }

  public availableSquads(filter?: string): void {
    this.squadService.squads().then(pageable => {
      let squads: ISquadModel[] = [];

      if (filter?.trim().length) {
         squads = filterByProperty(pageable.page, 'name', filter);
      } else {
        squads = pageable.page;
      }

      this.data = { ...this.data, squads, filter };
      this.contextData = { ...this.contextData, filter };
      this.contextDataService.setContextData(ContextDataInfo.SQUADS_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public selectedSquad(squad: ISquadModel): void {
    this.componetService.squadComponents(squad.id).then(pageable => {
      this.data = { ...this.data, squad, components: pageable.page };
      this.contextData = { ...this.contextData, squad };
      this.contextDataService.setContextData(ContextDataInfo.SQUADS_DATA, this.contextData, { persistent: true });

      this.summary$.next(this.data);
    });
  }

  public loadData(): void {
    this.availableSquads(this.contextData?.filter);

    if (this.contextData?.squad) {
      this.selectedSquad(this.contextData.squad);
    }
  }
}
