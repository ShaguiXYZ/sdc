/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
export interface IMetricDTO {
  id: number;
  name: string;
  type: string;
  validation: string;
  valueType: string;
}

export interface IMetricModel {
  id: number;
  name: string;
  type: string;
  validation: string;
  valueType: string;
}

export namespace IMetricModel {
  export const toModel = (dto: IMetricDTO): IMetricModel => new MetricModel(dto.id, dto.name, dto.type, dto.validation, dto.valueType);
  export const toDTO = (model: IMetricModel): IMetricDTO => ({ ...model });
}

export class MetricModel implements IMetricModel {
  constructor(public id: number, public name: string, public type: string, public validation: string, public valueType: string) {}
}
