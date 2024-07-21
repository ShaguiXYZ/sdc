/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export interface ITagDTO {
  name: string;
  analysisTag?: boolean;
}

export interface ITagModel {
  name: string;
  analysisTag?: boolean;
}

export namespace ITagModel {
  export const fromDTO = (dto: ITagDTO): ITagModel => new TagModel(dto.name, dto.analysisTag);
  export const toDTO = (model: ITagModel): ITagDTO => ({ ...model });
}

export class TagModel implements ITagModel {
  constructor(
    public name: string,
    public analysisTag?: boolean
  ) {}
}
