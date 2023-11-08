import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';
import { SdcGraphData } from 'src/app/shared/components/sdc-charts';

export interface SquadSummaryModel {
  components?: IComponentModel[];
  languageDistribution?: SdcGraphData;
  selectedTabIndex?: number;
  squad: ISquadModel;
}
