import { ContextDataServiceMock } from 'src/app/core/mock/services';

export class DepartmentContextDataServiceMock extends ContextDataServiceMock {
  override get() {
    return { squads: { id: 1, name: '', coverage: 1 }, department: { id: 1, name: '', coverage: 1 } };
  }
}
