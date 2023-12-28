import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ContextDataService } from 'src/app/core/services';
import { ComponentService } from 'src/app/core/services/sdc';
import { AppUrls } from 'src/app/shared/config/routing';
import { ContextDataInfo } from 'src/app/shared/constants';
import { SdcMetricsContextData } from 'src/app/shared/models';

@Injectable()
export class SdcEventItemService {
  constructor(
    private readonly componentSercice: ComponentService,
    private readonly contextDataService: ContextDataService,
    private readonly router: Router
  ) {}

  public navigateTo(componentId: number): void {
    // @howto: navigation to the metrics page, even if you are already on it
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.componentSercice.component(componentId).then(component => {
        this.contextDataService.set<SdcMetricsContextData>(ContextDataInfo.METRICS_DATA, { component });
        this.router.navigate([AppUrls.metrics]);
      });
    });
  }
}
