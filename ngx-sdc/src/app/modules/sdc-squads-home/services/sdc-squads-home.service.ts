import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { _console, filterBy } from 'src/app/core/lib';
import { ISquadModel } from 'src/app/core/models/sdc';
import { ContextDataService } from 'src/app/core/services';
import { ComponentService, SquadService } from 'src/app/core/services/sdc';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcSquadsContextData } from 'src/app/shared/models';
import { SdcSquadsDataModel } from '../models';

@Injectable()
export class SdcSquadsService {
  private contextData!: SdcSquadsContextData;
  private data$: Subject<Partial<SdcSquadsDataModel>>;

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly componetService: ComponentService,
    private readonly squadService: SquadService
  ) {
    this.data$ = new Subject();
    this.contextData = this.contextDataService.get(ContextDataInfo.SQUADS_DATA);
  }

  public onDataChange(): Observable<Partial<SdcSquadsDataModel>> {
    return this.data$.asObservable();
  }

  public availableSquads(filter?: string): void {
    this.squadService
      .squads()
      .then(pageable => {
        let squads: ISquadModel[] = [];
        const squad = pageable.page.find(data => this.contextData?.squad?.id === data.id);

        if (filter?.trim().length) {
          squads = filterBy(pageable.page, ['id', 'name'], filter);
        } else {
          squads = pageable.page;
        }

        this.contextData = { ...this.contextData, squad, filter };
        this.contextDataService.set(ContextDataInfo.SQUADS_DATA, this.contextData, { persistent: true });

        this.data$.next({ squad, squads, filter });
      })
      .catch(_console.error);
  }

  public selectedSquad(squad: ISquadModel): void {
    this.componetService
      .squadComponents(squad.id)
      .then(pageable => {
        this.contextData = { ...this.contextData, squad };
        this.contextDataService.set(ContextDataInfo.SQUADS_DATA, this.contextData, { persistent: true });

        this.data$.next({ squad, components: pageable.page });
      })
      .catch(_console.error);
  }

  public loadData(): void {
    this.availableSquads(this.contextData?.filter);

    if (this.contextData?.squad) {
      this.selectedSquad(this.contextData.squad);
    }
  }
}
