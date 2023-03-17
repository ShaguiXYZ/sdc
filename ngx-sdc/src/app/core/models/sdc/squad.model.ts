/* eslint-disable @typescript-eslint/no-namespace */

import { IDepartmentDTO, IDepartmentModel } from './department.model';

/* eslint-disable no-redeclare */
export interface ISquadDTO {
  id: number;
  name: string;
  department: IDepartmentDTO;
}

export interface ISquadModel {
  id: number;
  name: string;
  department: IDepartmentModel;
}

export namespace ISquadModel {
  export const toModel = (dto: ISquadDTO): ISquadModel => new SquadModel(dto.id, dto.name, IDepartmentModel.toModel(dto.department));
  export const toDTO = (model: ISquadModel): ISquadDTO => ({ ...model, department: IDepartmentModel.toDTO(model.department) });
}

export class SquadModel implements ISquadModel {
  constructor(public id: number, public name: string, public department: IDepartmentModel) {}
}
