/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { IAnalysisValuesDTO, IAnalysisValuesModel } from './analysis-values.model';
import { ICoverageModel } from './coverage.model';
import { IMetricDTO, IMetricModel } from './metric.model';

export interface IMetricAnalysisDTO {
  analysisDate: number;
  coverage: number;
  metric: IMetricDTO;
  analysisValues: IAnalysisValuesDTO;
  blocker?: boolean;
}

export interface IMetricAnalysisModel extends Omit<ICoverageModel, 'id'> {
  analysisDate: number;
  coverage: number;
  metric: IMetricModel;
  analysisValues: IAnalysisValuesModel;
  blocker?: boolean;
}

export namespace IMetricAnalysisModel {
  export const toModel = (dto: IMetricAnalysisDTO): IMetricAnalysisModel =>
    new MetricAnalysisModel(
      dto.analysisDate,
      dto.coverage,
      IMetricModel.toModel(dto.metric),
      dto.metric.name,
      IAnalysisValuesModel.toModel(dto.analysisValues),
      dto.blocker
    );
  export const toDTO = (model: IMetricAnalysisModel): IMetricAnalysisDTO => ({
    ...model,
    metric: IMetricModel.toDTO(model.metric),
    analysisValues: IAnalysisValuesModel.toDTO(model.analysisValues)
  });
}

export class MetricAnalysisModel implements IMetricAnalysisModel {
  constructor(
    public analysisDate: number,
    public coverage: number,
    public metric: IMetricModel,
    public name: string,
    public analysisValues: IAnalysisValuesModel,
    public blocker?: boolean
  ) {}
}
