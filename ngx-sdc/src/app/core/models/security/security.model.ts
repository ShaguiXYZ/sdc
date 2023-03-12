/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { ISessionModel } from './session.model';
import { IUserModel } from './userModel';

export const tokenKey = 'access_token';
export const sidKey = 'SID';

export interface UiSecurityInfo {
  session: ISessionModel;
  user: IUserModel;
}

export namespace UiSecurityInfo {
  export const getSession = (securityInfo: UiSecurityInfo): ISessionModel => securityInfo?.session;
  export const getUser = (securityInfo: UiSecurityInfo): IUserModel => securityInfo?.user;
  export const isLogged = (securityInfo: UiSecurityInfo): boolean => !securityInfo?.user && !securityInfo?.session;
}
