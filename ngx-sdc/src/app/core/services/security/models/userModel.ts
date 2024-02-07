/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */

export interface IUserDTO {
  userName: string;
  authorities: string[];
  email: string;

  // Person data
  name: string;
  surname: string;
  secondSurname: string;
}

export interface IUserModel {
  userName: string;
  authorities: string[];
  email: string;

  // Person data
  name: string;
  surname: string;
  secondSurname: string;
}

export class UserModel implements IUserModel {
  constructor(
    public userName: string,
    public email: string,
    public authorities: string[],
    public name: string,
    public surname: string,
    public secondSurname: string
  ) {}
}

export namespace IUserModel {
  export const fromDTO = (dto: IUserDTO): IUserModel =>
    new UserModel(dto.userName, dto.email, dto.authorities ?? [], dto.name, dto.surname, dto.secondSurname);

  export const toDTO = (model: IUserModel): IUserDTO => ({
    ...model,
    authorities: model.authorities ?? []
  });
}
