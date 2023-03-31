import { IPagingModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/components';

export interface SdcApplicationsModel {
  squadId: number;
  coverage: number;
  compliances: IComplianceModel[];
  paging: IPagingModel;
}
