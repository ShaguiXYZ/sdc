export interface IMetricDTO {
  id: number;
  name: string;
  type: string;
}

export interface IMetricModel {
  id: number;
  name: string;
  type: string;
}

export namespace IMetricModel {
  export const toModel = (dto: IMetricDTO): IMetricModel => new MetricModel(dto.id, dto.name, dto.type);
  export const toDTO = (model: IMetricModel): IMetricDTO => ({ ...model });
}

export class MetricModel implements IMetricModel {
  constructor(public id: number, public name: string, public type: string) {}
}
