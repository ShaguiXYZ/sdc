import { IComponentModel, ISquadModel } from 'src/app/core/models/sdc';

export interface SdcSummaryDataModel {
  components: IComponentModel[];
  squad?: ISquadModel;
  squads: ISquadModel[];
  filter?: string;
}
