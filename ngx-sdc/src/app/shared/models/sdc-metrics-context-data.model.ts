import { AnalysisFactor, IComponentModel, IMetricAnalysisModel } from 'src/app/core/models/sdc';

export interface MetricsContextData {
  component: IComponentModel;
  selected?: IMetricAnalysisModel;
  selectedTabIndex?: number;
  showFactorCharts?: Record<AnalysisFactor, boolean>;
}
