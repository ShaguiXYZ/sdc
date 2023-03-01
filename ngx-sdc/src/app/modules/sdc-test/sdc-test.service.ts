import { Injectable } from '@angular/core';
import { IComponentModel, IPageableModel, ISquadModel } from 'src/app/core/models';
import { SquadService } from 'src/app/core/services';

@Injectable()
export class SdcTestService {
  constructor(private squadService: SquadService) {}

  availableSquads(): Promise<IPageableModel<ISquadModel>> {
    return this.squadService.availableSquads();
  }

  componentsBySquad(squadId: number): Promise<IPageableModel<IComponentModel>> {
    return this.squadService.squadComponents(squadId);
  }
}
