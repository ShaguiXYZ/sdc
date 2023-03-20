import { IComponentModel } from 'src/app/core/models/sdc';

export interface SdcApplicationsModel {
  squadId: number;
  coverage: number;
  components: IComponentModel[];
}
