export interface ISquadDTO {
  id: number;
  name: string;
}

export interface ISquadModel {
  id: number;
  name: string;
}

export namespace ISquadModel {
  export const toModel = (dto: ISquadDTO): ISquadModel => new SquadModel(dto.id, dto.name);
  export const toDTO = (model: ISquadModel): ISquadDTO => ({ ...model });
}

export class SquadModel implements ISquadModel {
  constructor(public id: number, public name: string) {}
}
