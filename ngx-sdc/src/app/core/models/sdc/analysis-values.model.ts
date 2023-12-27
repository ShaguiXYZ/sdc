/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */

// @howto: define a type that is a subset of another type
export type AnalysisFactor = keyof Omit<IAnalysisValuesModel, 'metricValue'>;

export interface IAnalysisValuesDTO {
  metricValue: string;
  expectedValue?: string;
  goodValue?: string;
  perfectValue?: string;
}

export interface IAnalysisValuesModel {
  metricValue: string;
  expectedValue?: string;
  goodValue?: string;
  perfectValue?: string;
}

export namespace IAnalysisValuesModel {
  export const fromDTO = (dto: IAnalysisValuesDTO): IAnalysisValuesModel =>
    new AnalysisValuesModel(dto.metricValue, dto.expectedValue, dto.goodValue, dto.perfectValue);
  export const toDTO = (model: IAnalysisValuesModel): IAnalysisValuesDTO => ({ ...model });
}

export class AnalysisValuesModel implements IAnalysisValuesModel {
  constructor(
    public metricValue: string,
    public expectedValue?: string,
    public goodValue?: string,
    public perfectValue?: string
  ) {}
}
