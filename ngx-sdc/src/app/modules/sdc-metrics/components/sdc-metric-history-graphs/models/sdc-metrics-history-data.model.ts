import { IMetricAnalysisModel } from 'src/app/core/models/sdc';

export interface MetricsHistoryDataModel {
  componentAnalysis?: IMetricAnalysisModel[]; // All metrics analysis for a component
  historicalAnalysis?: IMetricAnalysisModel[]; // Metric history of the metric selected in the componentAnalysis
  selectedAnalysis?: IMetricAnalysisModel; // Metric selected in the componentAnalysis
}
