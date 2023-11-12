import { IPaging } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/models';

export interface SdcApplicationsDataModel {
  compliances: IComplianceModel[];
  coverage?: string;
  name?: string;
  squadId?: number;
  paging: IPaging;
}
