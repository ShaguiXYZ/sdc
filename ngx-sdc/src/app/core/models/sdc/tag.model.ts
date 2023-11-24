export interface ITagDTO {
  id: number;
  name: string;
  weight: number;
}

export interface ITagModel {
  id: number;
  name: string;
  weight: number;
}

export namespace ITagModel {
  export const toModel = (dto: ITagDTO): ITagModel => new TagModel(dto.id, dto.name, dto.weight);
  export const toDTO = (model: ITagModel): ITagDTO => ({ ...model });
}

export class TagModel implements ITagModel {
  constructor(public id: number, public name: string, public weight: number) {}
}
