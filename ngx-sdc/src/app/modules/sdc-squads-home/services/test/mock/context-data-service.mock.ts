import { UiContextDataServiceMock } from 'src/app/core/mock/services';

export class ContextDataServiceMock extends UiContextDataServiceMock {
  override get() {
    return { squads: undefined };
  }
}
