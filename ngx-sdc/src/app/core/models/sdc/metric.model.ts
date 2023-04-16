/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
export enum ValueType {
  NUMERIC = 'NUMERIC',
  VERSION = 'VERSION'
}

export enum ValidationType {
  MAYOR = 'MAYOR',
  MINOR = 'MINOR',
  EQUAL = 'EQUAL',
  MAYORorEQUAL = 'MAYORorEQUAL',
  MINORorEQUAL = 'MINORorEQUAL'
}

export interface IMetricDTO {
  id: number;
  name: string;
  type: string;
  validation?: ValidationType;
  valueType?: ValueType;
}

export interface IMetricModel {
  id: number;
  name: string;
  type: string;
  validation?: ValidationType;
  valueType?: ValueType;
}

export namespace IMetricModel {
  export const toModel = (dto: IMetricDTO): IMetricModel => new MetricModel(dto.id, dto.name, dto.type, dto.validation, dto.valueType);
  export const toDTO = (model: IMetricModel): IMetricDTO => ({ ...model });
}

export class MetricModel implements IMetricModel {
  constructor(public id: number, public name: string, public type: string, public validation?: ValidationType, public valueType?: ValueType) {}
}
