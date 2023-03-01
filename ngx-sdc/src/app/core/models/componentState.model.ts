import { IMetricAnalysisDTO, IMetricAnalysisModel } from './metricAnalysis.model';

export interface IComponentStateDTO {
  metricAnalysis: IMetricAnalysisDTO[];
  coverage: number;
}

export interface IComponentStateModel {
  metricAnalysis: IMetricAnalysisModel[];
  coverage: number;
}

export namespace IComponentStateModel {
  export const toModel = (dto: IComponentStateDTO): IComponentStateModel =>
    new ComponentStateModel(dto.metricAnalysis.map(IMetricAnalysisModel.toModel), dto.coverage);
  export const toDTO = (model: IComponentStateModel): IComponentStateDTO => ({
    ...model,
    metricAnalysis: model.metricAnalysis.map(IMetricAnalysisModel.toDTO)
  });
}

export class ComponentStateModel implements IComponentStateModel {
  constructor(public metricAnalysis: IMetricAnalysisModel[], public coverage: number) {}
}
