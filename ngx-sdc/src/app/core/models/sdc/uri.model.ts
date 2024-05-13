export enum UriType {
  GIT = 'GIT',
  SONAR = 'SONAR'
}

export interface IUriDTO {
  name: string;
  api: string;
  url: string;
}

export interface IUriModel {
  name: string;
  api: string;
  url: string;
}

export namespace IUriModel {
  export const fromDTO = (dto: IUriDTO): IUriModel => new UriModel(dto.name, dto.api, dto.url);
  export const toDTO = (model: IUriModel): IUriDTO => ({ ...model });
}

export class UriModel {
  constructor(
    public name: string,
    public api: string,
    public url: string
  ) {}
}
