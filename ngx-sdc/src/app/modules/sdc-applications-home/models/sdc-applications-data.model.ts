import { IComponentModel, IPaging } from 'src/app/core/models/sdc';

export interface SdcApplicationsDataModel {
  components: IComponentModel[];
  coverage?: string;
  name?: string;
  squadId?: number;
  paging: IPaging;
  tags?: string[];
}
