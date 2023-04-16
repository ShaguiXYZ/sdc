import { IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/components';

export interface MetricsDataModel {
  analysis?: IMetricAnalysisModel[];
  compliance: IComplianceModel;
  metrics?: IMetricModel[];
  selectedAnalysis?: IMetricAnalysisModel;
}
