import { Injectable } from '@angular/core';
import { hasValue } from 'src/app/core/lib';
import { SecurityService } from 'src/app/core/services';
import { ISecurityHeader } from '../models';

@Injectable()
export class HeaderSecurityService {
  private _securityInfo!: ISecurityHeader;

  constructor(private securityService: SecurityService) {
    this._securityInfo = { currentUser: this.securityService.user, isUserLogged: hasValue(this.securityService.user) };
  }

  get info(): ISecurityHeader {
    return this._securityInfo;
  }

  public signout() {
    this.securityService.logout();
  }
}
