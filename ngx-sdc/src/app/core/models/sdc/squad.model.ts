/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
import { IDepartmentDTO, IDepartmentModel } from './department.model';

export interface ISquadDTO {
  id: number;
  name: string;
  department: IDepartmentDTO;
  coverage?: number;
}

export interface ISquadModel {
  id: number;
  name: string;
  department: IDepartmentModel;
  coverage?: number;
}

export namespace ISquadModel {
  export const toModel = (dto: ISquadDTO): ISquadModel =>
    new SquadModel(dto.id, dto.name, IDepartmentModel.toModel(dto.department), dto.coverage);
  export const toDTO = (model: ISquadModel): ISquadDTO => ({ ...model, department: IDepartmentModel.toDTO(model.department) });
}

export class SquadModel implements ISquadModel {
  constructor(public id: number, public name: string, public department: IDepartmentModel, public coverage?: number) {}
}
