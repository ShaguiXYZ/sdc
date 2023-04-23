/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { IArchitectureDTO, IArchitectureModel } from './architecture.model';
import { IComponentTypeDTO, IComponentTypeModel } from './component-type.model';
import { ICoverageModel } from './coverage.model';
import { ISquadDTO, ISquadModel } from './squad.model';

export interface IComponentDTO {
  id: number;
  name: string;
  componentType: IComponentTypeDTO;
  architecture: IArchitectureDTO;
  squad: ISquadDTO;
  analysisDate?: number;
  coverage?: number;
}

export interface IComponentModel  extends ICoverageModel {
  architecture: IArchitectureModel;
  componentType: IComponentTypeModel;
  squad: ISquadModel;
  analysisDate?: number;
}

export namespace IComponentModel {
  export const toModel = (dto: IComponentDTO): IComponentModel =>
    new ComponentModel(
      dto.id,
      dto.name,
      IComponentTypeModel.toModel(dto.componentType),
      IArchitectureModel.toModel(dto.architecture),
      ISquadModel.toModel(dto.squad),
      dto.analysisDate,
      dto.coverage
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
    public architecture: IArchitectureModel,
    public componentType: IComponentTypeModel,
    public squad: ISquadModel,
    public analysisDate?: number,
    public coverage?: number
) {}
}
