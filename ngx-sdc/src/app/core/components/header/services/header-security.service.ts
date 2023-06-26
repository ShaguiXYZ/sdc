import { Injectable } from '@angular/core';
import { hasValue } from 'src/app/core/lib';
import { UiSecurityService } from 'src/app/core/services';
import { ISecurityModel } from 'src/app/core/services/security';
import { ISecurityHeader } from '../models';

@Injectable()
export class HeaderSecurityService {
  private _securityInfo!: ISecurityHeader;

  constructor(private securityService: UiSecurityService) {
    this._securityInfo = { currentUser: this.securityService.user, isUserLogged: hasValue(this.securityService.user) };
  }

  get info(): ISecurityHeader {
    return this._securityInfo;
  }

  public signout() {
    this.securityService.logout();
  }

  private updateSecurityData = (securityData: ISecurityModel) => {
    this._securityInfo = {
      currentUser: ISecurityModel.getUser(securityData),
      isItUser: this.securityService.isItUser(),
      isUserLogged: ISecurityModel.isLogged(securityData)
    };
  };
}
