import { IPagingModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/components';

export interface SdcApplicationsDataModel {
  compliances: IComplianceModel[];
  coverage?: string;
  name?: string;
  squadId?: number;
  paging: IPagingModel;
}
