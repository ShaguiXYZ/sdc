import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { SecurityService } from 'src/app/core/services';
import { SdcOverlayService } from '../../../services';
import { SdcLoginModel } from '../models';

@Injectable()
export class SdcLoginService {
  private data$: Subject<SdcLoginModel> = new Subject();

  constructor(
    private readonly securityService: SecurityService,
    private readonly overlayService: SdcOverlayService
  ) {
    this.securityService.forceLogout();
  }

  public onDataChange(): Observable<SdcLoginModel> {
    return this.data$.asObservable();
  }

  public login(value: { userName: string; password: string }): void {
    this.securityService.login(value).then(
      () => this.overlayService.toggleLogin(),
      error => this.data$.next({ authenticating: false, error })
    );
  }
}
