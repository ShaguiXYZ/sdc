export interface ITagDTO {
  name: string;
  weight?: number;
}

export interface ITagModel {
  name: string;
  weight?: number;
}

export namespace ITagModel {
  export const toModel = (dto: ITagDTO): ITagModel => new TagModel(dto.name, dto.weight);
  export const toDTO = (model: ITagModel): ITagDTO => ({ ...model });
}

export class TagModel implements ITagModel {
  constructor(public name: string, public weight?: number) {}
}
