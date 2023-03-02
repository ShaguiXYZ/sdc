import { IMetricAnalysisDTO, IMetricAnalysisModel } from './metric-analysis.model';

export interface IMetricAnalysisStateDTO {
  metricAnalysis: IMetricAnalysisDTO[];
  coverage: number;
}

export interface IMetricAnalysisStateModel {
  metricAnalysis: IMetricAnalysisModel[];
  coverage: number;
}

export namespace IMetricAnalysisStateModel {
  export const toModel = (dto: IMetricAnalysisStateDTO): IMetricAnalysisStateModel =>
    new MetricAnalysisStateModel(dto.metricAnalysis.map(IMetricAnalysisModel.toModel), dto.coverage);
  export const toDTO = (model: IMetricAnalysisStateModel): IMetricAnalysisStateDTO => ({
    ...model,
    metricAnalysis: model.metricAnalysis.map(IMetricAnalysisModel.toDTO)
  });
}

export class MetricAnalysisStateModel implements IMetricAnalysisStateModel {
  constructor(public metricAnalysis: IMetricAnalysisModel[], public coverage: number) {}
}
