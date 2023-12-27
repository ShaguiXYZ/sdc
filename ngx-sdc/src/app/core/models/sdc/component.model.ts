/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { ICoverageModel } from './coverage.model';
import { ISquadDTO, ISquadModel } from './squad.model';

export interface IComponentDTO {
  id: number;
  name: string;
  squad: ISquadDTO;
  analysisDate?: number;
  coverage?: number;
  trend?: number;
  blocked?: boolean;
}

export interface IComponentModel extends ICoverageModel {
  squad: ISquadModel;
  analysisDate?: number;
}

export namespace IComponentModel {
  export const fromDTO = (dto: IComponentDTO): IComponentModel =>
    new ComponentModel(dto.id, dto.name, ISquadModel.fromDTO(dto.squad), dto.analysisDate, dto.coverage, dto.trend, dto.blocked);
  export const toDTO = (model: IComponentModel): IComponentDTO => ({
    ...model,
    squad: ISquadModel.toDTO(model.squad)
  });
}

export class ComponentModel implements IComponentModel {
  constructor(
    public id: number,
    public name: string,
    public squad: ISquadModel,
    public analysisDate?: number,
    public coverage?: number,
    public trend?: number,
    public blocked?: boolean
  ) {}
}
