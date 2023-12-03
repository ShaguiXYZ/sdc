import { Inject, Injectable, Optional } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, NotificationService } from 'src/app/core/components/notification';
import { NX_CONTEX_CONFIG } from '../constatnts';
import { ContextDataService } from '../context-data.service';
import { urlInfoBykey } from '../lib';
import { ContextConfig } from '../models';

@Injectable({ providedIn: 'root' })
export class ContextValidGuard {
  constructor(
    @Optional() @Inject(NX_CONTEX_CONFIG) private readonly contextConfig: ContextConfig,
    private router: Router,
    private contextData: ContextDataService,
    private notificationService: NotificationService,
    private translateService: TranslateService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (route.routeConfig?.path) {
      const urlInfo = urlInfoBykey(route.routeConfig.path, this.contextConfig);
      const canNavigate = !urlInfo?.requiredData?.some(data => this.contextData.get(data) === undefined);

      if (!canNavigate) {
        this.notificationService.error(
          this.translateService.instant('Notifications.Error'),
          this.translateService.instant('Notifications.ContextParamsNotFound'),
          DEFAULT_TIMEOUT_NOTIFICATIONS,
          true
        );

        if (route.routeConfig.path !== this.contextConfig.home) {
          this.router.navigate([this.contextConfig.home]);
        }
      }

      return canNavigate;
    }

    return true;
  }
}
