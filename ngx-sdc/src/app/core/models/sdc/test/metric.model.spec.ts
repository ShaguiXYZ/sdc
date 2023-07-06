import { AnalysisType } from '../analysis-type.model';
import { MetricModel, ValidationType, ValueType } from '../metric.model';

describe('Model: metric analysis', () => {
  let model: MetricModel;

  it('should expect the model to be truthy', () => {
    model = new MetricModel(1, 'test', AnalysisType.GIT_XML, ValidationType.EQ, ValueType.NUMERIC);

    expect(model).toBeTruthy();
  });

  it('should expect the model to be truthy', () => {
    model = new MetricModel(1, 'test', AnalysisType.GIT_XML);

    expect(model).toBeTruthy();
  });
});
