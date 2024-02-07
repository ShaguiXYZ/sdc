import { Injectable } from '@angular/core';
import { SdcOverlayService } from '../../../services';
import { SecurityService } from 'src/app/core/services';

@Injectable()
export class SdcLoginService {
  constructor(
    private readonly securityService: SecurityService,
    private readonly overlayService: SdcOverlayService
  ) {}

  public login(value: { userName: string; password: string }): void {
    this.securityService.login(value).then(() => this.overlayService.toggleLogin());
  }
}
