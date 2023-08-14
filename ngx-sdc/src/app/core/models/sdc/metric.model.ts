/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */

import { AnalysisType } from './analysis-type.model';

export enum ValueType {
  NUMERIC = 'NUMERIC',
  BOOLEAN = 'BOOLEAN',
  VERSION = 'VERSION'
}

export enum ValidationType {
  GT = 'GT',
  LT = 'LT',
  EQ = 'EQ',
  NEQ = 'NEQ',
  GTE = 'GTE',
  LTE = 'LTE'
}

export interface IMetricDTO {
  id: number;
  name: string;
  type: AnalysisType;
  validation?: ValidationType;
  valueType?: ValueType;
}

export interface IMetricModel {
  id: number;
  name: string;
  type: AnalysisType;
  validation?: ValidationType;
  valueType?: ValueType;
}

export namespace IMetricModel {
  export const toModel = (dto: IMetricDTO): IMetricModel => new MetricModel(dto.id, dto.name, dto.type, dto.validation, dto.valueType);
  export const toDTO = (model: IMetricModel): IMetricDTO => ({ ...model });
}

export class MetricModel implements IMetricModel {
  constructor(
    public id: number,
    public name: string,
    public type: AnalysisType,
    public validation?: ValidationType,
    public valueType?: ValueType
  ) {}
}
