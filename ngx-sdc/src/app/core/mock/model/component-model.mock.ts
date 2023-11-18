import { IComponentModel } from '../../models/sdc';

export const componentModelMock: IComponentModel = {
  id: 1,
  name: 'component name',
  coverage: 90,
  squad: { id: 1, name: 'squad name', department: { id: 1, name: 'department name' } }
};
