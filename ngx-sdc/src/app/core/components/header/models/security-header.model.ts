import { IUserModel } from 'src/app/core/models/security';

export interface ISecurityHeader {
  currentUser?: IUserModel;
  isItUser?: boolean;
  isUserLogged: boolean;
}
