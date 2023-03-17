import { Injectable, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
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
    return this.squadService.availableSquads();
  }

  squadInfo(squadId: number): void {
    this.squadService.squadState(squadId).then(state => {
      const coverage = state.coverage;

      this.subject$.next({
        squadId,
        coverage
      });
    });
  }

  public ngOnDestroy() {
    console.log('...Destroy test service...');
  }
}
