import { Injectable } from '@angular/core';
import { ISdcSessionData } from '../models/session/session.model';
import { SquadService } from './sdc/squad.service';

@Injectable()
export class UiSessionService {
  private mockSquadId = 0;

  constructor(private squadService: SquadService) {}

  public async sdcSession(): Promise<ISdcSessionData> {
    const squad = await this.squadService.squad(this.mockSquadId, true);

    return { squad };
  }
}
