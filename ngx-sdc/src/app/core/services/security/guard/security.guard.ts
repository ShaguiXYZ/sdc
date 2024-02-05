import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { CONTEXT_SECURITY_KEY, ISecurityModel, SecurityService } from '../..';
import { ContextConfig, ContextDataService, NX_CONTEX_CONFIG } from '../../context-data';
import { inject } from '@angular/core';

export const authGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const contextData: ContextDataService = inject(ContextDataService);
  const securityService: SecurityService = inject(SecurityService);

  const securityInfo = contextData.get(CONTEXT_SECURITY_KEY);

  if (ISecurityModel.isLogged(securityInfo)) {
    // logged in so return true
    return true;
  }

  // not logged in so redirect to login page with the return url
  securityService.logout();

  return false;
};

export const publicGuard = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const contextConfig: ContextConfig = inject(NX_CONTEX_CONFIG, { optional: true }) ?? ({} as ContextConfig);
  const contextData: ContextDataService = inject(ContextDataService);
  const router: Router = inject(Router);

  const securityInfo = contextData.get(CONTEXT_SECURITY_KEY);

  if (ISecurityModel.isLogged(securityInfo)) {
    return router.createUrlTree([contextConfig.home]);
  }

  return true;
};
