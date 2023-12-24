import { AnalysisFactor, IComponentModel, IMetricAnalysisModel } from 'src/app/core/models/sdc';

export interface SdcMetricsContextData {
  component: IComponentModel;
  selected?: IMetricAnalysisModel;
  selectedTabIndex?: number;
  showFactorCharts?: Record<AnalysisFactor, boolean>;
}
