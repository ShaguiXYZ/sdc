import { IArchitectureDTO, IArchitectureModel } from './architecture.model';
import { IComponentTypeDTO, IComponentTypeModel } from './component-type.model';
import { ISquadDTO, ISquadModel } from './squad.model';

export interface IComponentDTO {
  id: number;
  name: string;
  componentType: IComponentTypeDTO;
  architecture: IArchitectureDTO;
  squad: ISquadDTO;
}

export interface IComponentModel {
  id: number;
  name: string;
  componentType: IComponentTypeModel;
  architecture: IArchitectureModel;
  squad: ISquadModel;
}

export namespace IComponentModel {
  export const toModel = (dto: IComponentDTO): IComponentModel =>
    new ComponentModel(
      dto.id,
      dto.name,
      IComponentTypeModel.toModel(dto.componentType),
      IArchitectureModel.toModel(dto.architecture),
      ISquadModel.toModel(dto.squad)
    );
  export const toDTO = (model: IComponentModel): IComponentDTO => ({
    ...model,
    componentType: IComponentTypeModel.toDTO(model.componentType),
    architecture: IArchitectureModel.toDTO(model.architecture),
    squad: ISquadModel.toDTO(model.squad)
  });
}

export class ComponentModel implements IComponentModel {
  constructor(
    public id: number,
    public name: string,
    public componentType: IComponentTypeModel,
    public architecture: IArchitectureModel,
    public squad: ISquadModel
  ) {}
}
