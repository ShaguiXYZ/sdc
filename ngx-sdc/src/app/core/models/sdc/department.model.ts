/* eslint-disable @typescript-eslint/no-namespace */
/* eslint-disable no-redeclare */
export interface IDepartmentDTO {
  id: number;
  name: string;
}

export interface IDepartmentModel {
  id: number;
  name: string;
}

export namespace IDepartmentModel {
  export const toModel = (dto: IDepartmentDTO): IDepartmentModel => new DepartmentModel(dto.id, dto.name);
  export const toDTO = (model: IDepartmentModel): IDepartmentDTO => ({ ...model });
}

export class DepartmentModel implements IDepartmentModel {
  constructor(public id: number, public name: string) {}
}
