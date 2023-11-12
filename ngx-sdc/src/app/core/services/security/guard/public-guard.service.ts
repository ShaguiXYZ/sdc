import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { ContextDataService } from '../..';
import { CONTEXT_SECURITY_KEY } from '../constants';
import { ISecurityModel } from '../models';

@Injectable({ providedIn: 'root' })
export class PublicGuard {
  constructor(private router: Router, private contextData: ContextDataService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const securityInfo = this.contextData.get(CONTEXT_SECURITY_KEY);

    if (ISecurityModel.isLogged(securityInfo)) {
      this.router.navigate(['']);
      return false;
    }

    return true;
  }
}
