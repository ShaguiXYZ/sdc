import { IAnalysisValuesModel } from '../analysis-values.model';
import { MetricAnalysisModel } from '../metric-analysis.model';
import { IMetricModel } from '../metric.model';

describe('Model: metric analysis', () => {
  let model: MetricAnalysisModel;
  let metric: IMetricModel;
  let analysisValues: IAnalysisValuesModel;

  it('should expect the model to be truthy', () => {
    model = new MetricAnalysisModel(1, 1, metric, analysisValues);

    expect(model).toBeTruthy();
  });
});
