import { of } from 'rxjs';
import { ISquadModel } from 'src/app/core/models/sdc';

export class SdcSquadsServiceMock {
  onDataChange() {
    return of({ components: [], squads: [] });
  }

  loadData() {
    /* Mock method */
  }

  availableSquads(filter?: string): void {
    /* Mock method */
  }

  selectedSquad(squad: ISquadModel): void {
    /* Mock method */
  }
}
