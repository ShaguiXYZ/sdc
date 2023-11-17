import { AnalysisType } from '../analysis-type.model';
import { IAnalysisValuesModel } from '../analysis-values.model';
import { MetricAnalysisModel } from '../metric-analysis.model';
import { IMetricModel } from '../metric.model';

describe('Model: metric analysis', () => {
  let model: MetricAnalysisModel;
  const metric: IMetricModel = {
    id: 1,
    name: 'metric name',
    type: AnalysisType.GIT_XML
  };
  let analysisValues: IAnalysisValuesModel;

  it('should expect the model to be truthy', () => {
    model = new MetricAnalysisModel(1, 1, metric, metric.name, analysisValues, false);

    expect(model).toBeTruthy();
  });
});
