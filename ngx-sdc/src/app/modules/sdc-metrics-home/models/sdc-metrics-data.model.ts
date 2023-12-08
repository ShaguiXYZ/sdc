import { IComponentModel, IMetricAnalysisModel, ITagModel } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { SdcChartData } from 'src/app/shared/models';

export interface MetricsDataModel {
  tags?: ITagModel[];
  component: IComponentModel;
  historical?: IHistoricalCoverage<IComponentModel>;
  languageDistribution?: SdcChartData;
  selectedAnalysis?: IMetricAnalysisModel;
  selectedTabIndex?: number;
}
