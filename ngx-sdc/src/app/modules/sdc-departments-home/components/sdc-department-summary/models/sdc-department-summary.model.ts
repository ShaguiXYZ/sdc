import { IDepartmentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcGraphData } from 'src/app/shared/components/sdc-charts';

export interface DepartmentSummaryModel {
  department: IDepartmentModel;
  languageDistribution?: SdcGraphData;
  selectedTabIndex?: number;
  squads?: ISquadModel[];
}
