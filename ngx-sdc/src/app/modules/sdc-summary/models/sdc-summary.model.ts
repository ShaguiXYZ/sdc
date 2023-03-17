import { IComponentModel, SquadModel } from 'src/app/core/models/sdc';

export interface SdcSummaryModel {
  squad: SquadModel;
  squadCoverage?: number;
  components?: IComponentModel[];
}
