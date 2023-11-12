import { ContextDataServiceMock } from 'src/app/core/mock/services';

export class SquadContextDataServiceMock extends ContextDataServiceMock {
  override get() {
    return { squads: undefined };
  }
}
