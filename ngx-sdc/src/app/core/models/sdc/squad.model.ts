/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { ICoverageModel } from './coverage.model';
import { IDepartmentDTO, IDepartmentModel } from './department.model';

export interface ISquadDTO {
  id: number;
  name: string;
  department: IDepartmentDTO;
  coverage?: number;
}

export interface ISquadModel extends ICoverageModel {
  department: IDepartmentModel;
}

export namespace ISquadModel {
  export const toModel = (dto: ISquadDTO): ISquadModel =>
    new SquadModel(dto.id, dto.name, IDepartmentModel.toModel(dto.department), dto.coverage);
  export const toDTO = (model: ISquadModel): ISquadDTO => ({ ...model, department: IDepartmentModel.toDTO(model.department) });
}

export class SquadModel implements ISquadModel {
  constructor(public id: number, public name: string, public department: IDepartmentModel, public coverage?: number) {}
}
