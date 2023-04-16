import { Inject, Injectable, Optional } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { UiContextDataService } from '../context-data.service';
import { routerData, urlInfoBykey } from '../lib';
import { ContextConfig, NX_CONTEX_CONFIG, RouterInfo } from '../models';
import { UiNotificationService } from 'src/app/core/components/notification';
import { TranslateService } from '@ngx-translate/core';
import { DEFAULT_TIMEOUT_NOTIFICATIONS } from 'src/app/core/constants/app.constants';

@Injectable({ providedIn: 'root' })
export class ContextValidGuard implements CanActivate {
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
      const canNavigate = !urlInfo.requiredData?.some(data => this.contextData.getContextData(data) === undefined);

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
