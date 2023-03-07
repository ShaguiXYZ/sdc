/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
export interface IComponentTypeDTO {
  id: number;
  name: string;
}

export interface IComponentTypeModel {
  id: number;
  name: string;
}

export namespace IComponentTypeModel {
  export const toModel = (dto: IComponentTypeDTO): IComponentTypeModel => new ComponentTypeModel(dto.id, dto.name);
  export const toDTO = (model: IComponentTypeModel): IComponentTypeDTO => ({ ...model });
}

export class ComponentTypeModel implements IComponentTypeModel {
  constructor(public id: number, public name: string) {}
}
