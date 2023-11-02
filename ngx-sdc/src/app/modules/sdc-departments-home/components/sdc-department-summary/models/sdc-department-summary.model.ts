import { ISquadModel } from 'src/app/core/models/sdc';
import { SdcGraphData } from 'src/app/shared/models';

export interface DepartmentSummaryModel {
  languageDistribution?: SdcGraphData;
  selectedTabIndex?: number;
  squads?: ISquadModel[];
}
