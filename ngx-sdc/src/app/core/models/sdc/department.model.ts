/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { ICoverageModel } from './coverage.model';

export interface IDepartmentDTO {
  id: number;
  name: string;
  coverage?: number;
}

export type IDepartmentModel = ICoverageModel;

export namespace IDepartmentModel {
  export const toModel = (dto: IDepartmentDTO): IDepartmentModel => new DepartmentModel(dto.id, dto.name, dto.coverage);
  export const toDTO = (model: IDepartmentModel): IDepartmentDTO => ({ ...model });
}

export class DepartmentModel implements IDepartmentModel {
  constructor(public id: number, public name: string, public coverage?: number) {}
}
