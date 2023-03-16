import { Injectable, OnDestroy } from '@angular/core';
import { filter, Observable, Subject, Subscription } from 'rxjs';
import { ContextDataNames } from 'src/app/shared/config/contextInfo';
import { UiSecurityInfo } from '../../../models/security/security.model';
import { UiAppContextData, UiSecurityService } from '../../../services';
import { ISecurityHeader } from '../models';

@Injectable()
export class HeaderSecurityService implements OnDestroy {
  private _info: ISecurityHeader = { isUserLogged: false };
  private securityChange$: Subject<ISecurityHeader>;
  private security$: Subscription;

  constructor(private appContextData: UiAppContextData, private securityService: UiSecurityService) {
    this.securityChange$ = new Subject<ISecurityHeader>();
    this.updateSecurityData();

    this.security$ = this.appContextData
      .onDataChange()
      .pipe(filter(key => key === ContextDataNames.securityInfo))
      .subscribe(() => {
        this.updateSecurityData();
      });
  }

  public ngOnDestroy() {
    this.security$.unsubscribe();
  }

  get info(): ISecurityHeader {
    return this._info;
  }

  public onSecurityChange(): Observable<ISecurityHeader> {
    return this.securityChange$.asObservable();
  }

  public signout() {
    this.securityService.logout();
  }

  private updateSecurityData = () => {
    this._info = {
      currentUser: UiSecurityInfo.getUser(this.appContextData.securityInfo),
      isItUser: this.securityService.isItUser(),
      isUserLogged: UiSecurityInfo.isLogged(this.appContextData.securityInfo)
    };

    this.securityChange$.next(this._info);
  };
}
