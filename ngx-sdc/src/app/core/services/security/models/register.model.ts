import { IUserModel } from '.';

export interface IRegisterModel extends Partial<IUserModel> {
    password?: string;
    resource?: string;
}
