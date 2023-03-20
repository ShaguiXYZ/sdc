import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { componentsCoverage } from 'src/app/core/lib/sdc.utils';
import { IPageableModel, ISquadModel } from 'src/app/core/models/sdc';
import { SquadService } from 'src/app/core/services';
import { SdcApplicationsModel } from '../models';

@Injectable()
export class SdcApplicationsService implements OnDestroy {
  private subject$: Subject<SdcApplicationsModel>;

  constructor(private squadService: SquadService) {
    this.subject$ = new Subject();
  }

  public onDataChange(): Observable<SdcApplicationsModel> {
    return this.subject$.asObservable();
  }

  availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.squads();
  }

  squadData(squadId: number): void {
    this.squadService.squadComponents(squadId).then(pageable => {
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
