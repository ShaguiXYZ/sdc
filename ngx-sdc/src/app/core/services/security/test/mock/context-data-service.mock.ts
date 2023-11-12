import { ContextDataServiceMock } from 'src/app/core/mock/services';
import { AppAuthorities, ISecurityModel, IUserModel } from '../../models';

export const securityModel: ISecurityModel = {
  session: { sid: '', token: '' },
  user: { userName: '', authorities: [{ authority: AppAuthorities.business }], email: '', name: '', surname: '', secondSurname: '' }
};

export const user: IUserModel = {
  userName: '',
  authorities: [{ authority: AppAuthorities.business }],
  email: '',
  name: '',
  surname: '',
  secondSurname: ''
};

export class SecurityContextDataServiceMock extends ContextDataServiceMock {
  override get() {
    return securityModel;
  }
}
