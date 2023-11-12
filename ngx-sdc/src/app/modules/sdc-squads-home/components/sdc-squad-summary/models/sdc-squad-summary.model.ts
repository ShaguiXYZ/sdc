import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcGraphData } from 'src/app/shared/models';

export interface SquadSummaryModel {
  components?: IComponentModel[];
  languageDistribution?: SdcGraphData;
  selectedTabIndex?: number;
  squad: ISquadModel;
}
