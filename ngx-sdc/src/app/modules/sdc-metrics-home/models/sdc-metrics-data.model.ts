import { IComponentModel, IMetricAnalysisModel, ITagModel } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { SdcGraphData } from 'src/app/shared/models';

export interface MetricsDataModel {
  tags?: ITagModel[];
  component: IComponentModel;
  historical?: IHistoricalCoverage<IComponentModel>;
  languageDistribution?: SdcGraphData;
  selectedAnalysis?: IMetricAnalysisModel;
  selectedTabIndex?: number;
}
