/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { IAnalysisValuesDTO, IAnalysisValuesModel } from './analysis-values.model';
import { IMetricDTO, IMetricModel } from './metric.model';

export interface IMetricAnalysisDTO {
  analysisDate: Date;
  metric: IMetricDTO;
  analysisValues: IAnalysisValuesDTO;
  state: string;
}

export interface IMetricAnalysisModel {
  analysisDate: Date;
  metric: IMetricModel;
  analysisValues: IAnalysisValuesModel;
  state: string;
}

export namespace IMetricAnalysisModel {
  export const toModel = (dto: IMetricAnalysisDTO): IMetricAnalysisModel =>
    new MetricAnalysisModel(
      dto.analysisDate,
      IMetricModel.toModel(dto.metric),
      IAnalysisValuesModel.toModel(dto.analysisValues),
      dto.state
    );
  export const toDTO = (model: IMetricAnalysisModel): IMetricAnalysisDTO => ({
    ...model,
    metric: IMetricModel.toDTO(model.metric),
    analysisValues: IAnalysisValuesModel.toDTO(model.analysisValues)
  });
}

export class MetricAnalysisModel implements IMetricAnalysisModel {
  constructor(public analysisDate: Date, public metric: IMetricModel, public analysisValues: IAnalysisValuesModel, public state: string) {}
}
