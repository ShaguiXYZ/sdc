import { IArchitectureDTO, IArchitectureModel } from './architectureModel';
import { IComponentTypeDTO, IComponentTypeModel } from './componentTypeModel';

export interface IComponentDTO {
  id: number;
  name: string;
  componentType: IComponentTypeDTO;
  architecture: IArchitectureDTO;
}

export interface IComponentModel {
  id: number;
  name: string;
  componentType: IComponentTypeModel;
  architecture: IArchitectureModel;
}

export namespace IComponentModel {
  export const toModel = (dto: IComponentDTO): IComponentModel =>
    new ComponentModel(dto.id, dto.name, IComponentTypeModel.toModel(dto.componentType), IArchitectureModel.toModel(dto.architecture));
  export const toDTO = (model: IComponentModel): IComponentDTO => ({
    ...model,
    componentType: IComponentTypeModel.toDTO(model.componentType),
    architecture: IArchitectureModel.toDTO(model.architecture)
  });
}

export class ComponentModel implements IComponentModel {
  constructor(public id: number, public name: string, public componentType: IComponentTypeModel, public architecture: IArchitectureModel) {}
}
