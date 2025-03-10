/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */

import { AnalysisType } from './analysis-type.model';

export enum ValueType {
  NUMERIC = 'NUMERIC',
  NUMERIC_MAP = 'NUMERIC_MAP',
  BOOLEAN = 'BOOLEAN',
  VERSION = 'VERSION'
}

/**
 * @howto use this type to define a variable that can hold any of the values in the enum
 */
const EVALUABLEVALUETYPE = [ValueType.NUMERIC, ValueType.BOOLEAN, ValueType.VERSION] as const;
export type EvaluableValueType = (typeof EVALUABLEVALUETYPE)[number];

export namespace EvaluableValueType {
  export const isEvaluableValueType = (value: ValueType): value is EvaluableValueType =>
    EVALUABLEVALUETYPE.includes(value as EvaluableValueType);
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
  export const fromDTO = (dto: IMetricDTO): IMetricModel => new MetricModel(dto.id, dto.name, dto.type, dto.validation, dto.valueType);
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
