import { UiContextDataServiceMock } from 'src/app/core/mock/services';

export class ContextDataServiceMock extends UiContextDataServiceMock {
  override get() {
    return { squads: { id: 1, name: '', coverage: 1 }, department: { id: 1, name: '', coverage: 1 } };
  }
}
