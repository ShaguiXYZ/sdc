/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { ICoverageModel } from './coverage.model';

export interface IDepartmentDTO {
  id: number;
  name: string;
  coverage?: number;
  trend?: number;
}

export type IDepartmentModel = ICoverageModel;

export namespace IDepartmentModel {
  export const fromDTO = (dto: IDepartmentDTO): IDepartmentModel => new DepartmentModel(dto.id, dto.name, dto.coverage, dto.trend);
  export const toDTO = (model: IDepartmentModel): IDepartmentDTO => ({ ...model });
}

export class DepartmentModel implements IDepartmentModel {
  constructor(
    public id: number,
    public name: string,
    public coverage?: number,
    public trend?: number
  ) {}
}
