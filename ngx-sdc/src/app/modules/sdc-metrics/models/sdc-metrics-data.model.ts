import { IComponentModel, IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { IHistoricalCoverage } from 'src/app/core/models/sdc/historical-coverage.model';
import { IComplianceModel } from 'src/app/shared/components';

export interface MetricsDataModel {
  analysis?: IMetricAnalysisModel[];
  compliance: IComplianceModel;
  metrics?: IMetricModel[];
  historical?: IHistoricalCoverage<IComponentModel>;
  selectedAnalysis?: IMetricAnalysisModel;
}
