export interface IArchitectureDTO {
  id: number;
  name: string;
}

export interface IArchitectureModel {
  id: number;
  name: string;
}

export namespace IArchitectureModel {
  export const toModel = (dto: IArchitectureDTO): IArchitectureModel => new ArchitectureModel(dto.id, dto.name);
  export const toDTO = (model: IArchitectureModel): IArchitectureDTO => ({ ...model });
}

export class ArchitectureModel implements IArchitectureModel {
  constructor(public id: number, public name: string) {}
}
