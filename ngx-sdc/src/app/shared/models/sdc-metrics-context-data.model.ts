import { IMetricAnalysisModel } from 'src/app/core/models/sdc';
import { IComplianceModel } from './compliance.model';

export interface MetricsContextData {
  compliance: IComplianceModel;
  selected?: IMetricAnalysisModel;
  selectedTabIndex?: number;
}
