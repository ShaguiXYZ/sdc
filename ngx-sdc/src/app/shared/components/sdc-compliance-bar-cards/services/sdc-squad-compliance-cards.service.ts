import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { IComponentModel } from 'src/app/core/models/sdc';
import { SquadService } from 'src/app/core/services';

@Injectable()
export class SdcComplianceBarCardsService {
  private subject$: Subject<IComponentModel[]>;

  constructor(private squadService: SquadService) {
    this.subject$ = new Subject();
  }

  squadComponents(squadId: number): void {
    this.squadService.squadComponents(squadId).then(pageable => this.subject$.next(pageable.page));
  }

  public onDataChange(): Observable<IComponentModel[]> {
    return this.subject$.asObservable();
  }
}
