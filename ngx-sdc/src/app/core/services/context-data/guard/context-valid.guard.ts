import { Inject, Injectable, Optional } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS, UiNotificationService } from 'src/app/core/components/notification';
import { NX_CONTEX_CONFIG } from '../constatnts';
import { UiContextDataService } from '../context-data.service';
import { urlInfoBykey } from '../lib';
import { ContextConfig } from '../models';

@Injectable({ providedIn: 'root' })
export class ContextValidGuard {
  constructor(
    @Optional() @Inject(NX_CONTEX_CONFIG) private contextConfig: ContextConfig,
    private router: Router,
    private contextData: UiContextDataService,
    private notificationService: UiNotificationService,
    private translateService: TranslateService
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (route.routeConfig?.path) {
      const urlInfo = urlInfoBykey(route.routeConfig.path, this.contextConfig);
      const canNavigate = !urlInfo?.requiredData?.some(data => this.contextData.get(data) === undefined);

      if (!canNavigate) {
        this.notificationService.error(
          this.translateService.instant('Navigation.Error'),
          this.translateService.instant('Navigation.ConstextParams.Error'),
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
