/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { IAuthorityDTO, IAuthorityModel } from '.';

export interface IUserDTO {
    userName: string;
    authorities: IAuthorityDTO[];
    email: string;

    // Person data
    name: string;
    surname: string;
    secondSurname: string;
}

export interface IUserModel {
    userName: string;
    authorities: IAuthorityModel[];
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
        public authorities: IAuthorityModel[],
        public name: string,
        public surname: string,
        public secondSurname: string
    ) {}
}

export namespace IUserModel {
    export const toModel = (dto: IUserDTO): IUserModel =>
        new UserModel(
            dto.userName,
            dto.email,
            dto.authorities ? dto.authorities.map(auth => IAuthorityModel.toModel(auth)) : [],
            dto.name,
            dto.surname,
            dto.secondSurname
        );

    export const toDTO = (model: IUserModel): IUserDTO => ({
        ...model,
        authorities: model.authorities ? model.authorities.map(auth => IAuthorityModel.toDTO(auth)) : []
    });
}
