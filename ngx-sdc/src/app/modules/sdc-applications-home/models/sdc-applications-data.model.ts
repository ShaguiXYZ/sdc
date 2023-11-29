import { IComponentModel, IPaging } from 'src/app/core/models/sdc';
import { MetricStates } from 'src/app/shared/lib';

export interface SdcApplicationsDataModel {
  components: IComponentModel[];
  coverage?: MetricStates;
  name?: string;
  squadId?: number;
  paging: IPaging;
  tags?: string[];
}
