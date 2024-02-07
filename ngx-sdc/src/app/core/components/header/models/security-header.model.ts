import { IUserModel } from 'src/app/core/services/security';

export interface ISecurityHeader {
  currentUser?: IUserModel;
  isUserLogged: boolean;
}
