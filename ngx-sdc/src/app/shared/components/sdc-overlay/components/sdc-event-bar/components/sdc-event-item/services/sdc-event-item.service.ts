import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { SdcRouteService } from 'src/app/core/services/sdc';

@Injectable()
export class SdcEventItemService {
  constructor(
    private readonly router: Router,
    private readonly routerService: SdcRouteService
  ) {}

  public navigateTo(componentId: number): void {
    // @howto navigation to the metrics page, even if you are already on it
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.routerService.toComponentById(componentId);
    });
  }
}
