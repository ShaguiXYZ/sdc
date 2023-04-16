/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { IAnalysisValuesDTO, IAnalysisValuesModel } from './analysis-values.model';
import { IMetricDTO, IMetricModel } from './metric.model';

export interface IMetricAnalysisDTO {
  analysisDate: number;
  coverage: number;
  metric: IMetricDTO;
  analysisValues: IAnalysisValuesDTO;
}

export interface IMetricAnalysisModel {
  analysisDate: number;
  coverage: number;
  metric: IMetricModel;
  analysisValues: IAnalysisValuesModel;
}

export namespace IMetricAnalysisModel {
  export const toModel = (dto: IMetricAnalysisDTO): IMetricAnalysisModel =>
    new MetricAnalysisModel(
      dto.analysisDate,
      dto.coverage,
      IMetricModel.toModel(dto.metric),
      IAnalysisValuesModel.toModel(dto.analysisValues)
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
    public analysisValues: IAnalysisValuesModel
  ) {}
}
