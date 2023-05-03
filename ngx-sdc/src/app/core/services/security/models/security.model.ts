/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { ISessionModel } from './session.model';
import { IUserModel } from './userModel';

export const tokenKey = 'access_token';
export const sidKey = 'SID';

export interface ISecurityModel {
  session: ISessionModel;
  user: IUserModel;
}

export namespace ISecurityModel {
  export const getSession = (securityInfo: ISecurityModel): ISessionModel => securityInfo?.session;
  export const getUser = (securityInfo: ISecurityModel): IUserModel => securityInfo?.user;
  export const isLogged = (securityInfo: ISecurityModel): boolean => !securityInfo?.user && !securityInfo?.session;
}
