import { IMetricAnalysisModel, IMetricModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from 'src/app/shared/components';

export interface MetricsContextData {
  compliance: IComplianceModel;
  selected?: IMetricAnalysisModel;
}
