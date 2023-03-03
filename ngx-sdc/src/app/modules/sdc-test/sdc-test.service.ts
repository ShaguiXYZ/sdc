import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IComponentModel, IPageableModel, ISquadModel } from 'src/app/core/models';
import { SquadService } from 'src/app/core/services';

export interface ISquadInfo {
  components: IComponentModel[];
  coverage: number;
}

@Injectable()
export class SdcTestService {
  private subject$: Subject<ISquadInfo>;

  constructor(private squadService: SquadService) {
    this.subject$ = new Subject<ISquadInfo>();
  }

  public onDataChange(): Observable<ISquadInfo> {
    return this.subject$.asObservable();
  }

  availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.availableSquads();
  }

  squadInfo(squadId: number): void {
    this.squadService.squadComponents(squadId).then(pageable => {
      const components = pageable.page;
      const compoentsWithCovetage = components.filter(item => item.coverage);
      const coverage = compoentsWithCovetage.reduce((sum, current) => sum + current.coverage!, 0) / compoentsWithCovetage.length;

      this.subject$.next({
        components: components,
        coverage: coverage
      })
    });
  }
}
