import { IPagingModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/components';

export interface SdcApplicationsDataModel {
  // coverage: number;
  compliances: IComplianceModel[];
  name?: string;
  squadId?: number;
  paging: IPagingModel;
}
