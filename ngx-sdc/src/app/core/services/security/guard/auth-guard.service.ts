import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ContextDataService } from '../..';
import { CONTEXT_SECURITY_KEY } from '../constants';
import { ISecurityModel } from '../models';
import { SecurityService } from '../security.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard {
  constructor(private securityService: SecurityService, private contextData: ContextDataService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const securityInfo = this.contextData.get(CONTEXT_SECURITY_KEY);

    if (ISecurityModel.isLogged(securityInfo)) {
      // logged in so return true
      return true;
    }

    // not logged in so redirect to login page with the return url
    this.securityService.logout();

    return false;
  }
}
