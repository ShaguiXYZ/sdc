import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, NotificationService } from 'src/app/core/components/notification';
import { NX_CONTEX_CONFIG } from '../constatnts';
import { ContextDataService } from '../context-data.service';
import { urlInfoBykey } from '../lib';
import { ContextConfig } from '../models';

export const contextValidGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const contextConfig: ContextConfig = inject(NX_CONTEX_CONFIG, { optional: true }) ?? ({} as ContextConfig);
  const contextData: ContextDataService = inject(ContextDataService);
  const notificationService: NotificationService = inject(NotificationService);
  const router: Router = inject(Router);
  const translateService: TranslateService = inject(TranslateService);

  if (route.routeConfig?.path) {
    const urlInfo = urlInfoBykey(route.routeConfig.path, contextConfig);
    const incompleteContext = urlInfo?.requiredData?.some(data => contextData.get(data) === undefined);

    if (incompleteContext) {
      notificationService.error(
        translateService.instant('Notifications.Error'),
        translateService.instant('Notifications.ContextParamsNotFound'),
        DEFAULT_TIMEOUT_NOTIFICATIONS,
        true
      );

      if (route.routeConfig.path !== contextConfig.home) {
        return router.createUrlTree([contextConfig.home]);
      }
    }

    return !incompleteContext;
  }

  return true;
};
