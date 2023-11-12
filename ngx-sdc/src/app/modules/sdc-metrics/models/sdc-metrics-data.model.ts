import { IComponentModel, IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { IComplianceModel, SdcGraphData } from 'src/app/shared/models';

export interface MetricsDataModel {
  compliance: IComplianceModel;
  historical?: IHistoricalCoverage<IComponentModel>;
  languageDistribution?: SdcGraphData;
  selectedAnalysis?: IMetricAnalysisModel;
  selectedTabIndex?: number;
}
