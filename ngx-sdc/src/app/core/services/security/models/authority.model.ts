/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
export enum AppAuthorities {
  business = 'BUSINESS',
  it = 'IT'
}

export interface IAuthorityDTO {
  authority: AppAuthorities;
}

export interface IAuthorityModel {
  authority: AppAuthorities;
}

export class AuthorityModel implements IAuthorityModel {
  constructor(public authority: AppAuthorities) {}
}

export namespace IAuthorityModel {
  export const fromDTO = (dto: IAuthorityDTO): IAuthorityModel => new AuthorityModel(dto.authority);

  export const toDTO = (model: AuthorityModel): IAuthorityDTO => ({
    authority: model.authority
  });
}
