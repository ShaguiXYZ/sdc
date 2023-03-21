import { Injectable, OnDestroy } from '@angular/core';
import { filter, Observable, Subject, Subscription } from 'rxjs';
import { CoreContextDataNames } from 'src/app/core/services/context-data';
import { UiSecurityInfo } from '../../../models/security/security.model';
import { UiAppContextDataService, UiSecurityService } from '../../../services';
import { ISecurityHeader } from '../models';

@Injectable()
export class HeaderSecurityService implements OnDestroy {
  private _info: ISecurityHeader = { isUserLogged: false };
  private securityChange$: Subject<ISecurityHeader>;
  private security$: Subscription;

  constructor(private appContextData: UiAppContextDataService, private securityService: UiSecurityService) {
    this.securityChange$ = new Subject<ISecurityHeader>();
    this.updateSecurityData();

    this.security$ = this.appContextData
      .onDataChange()
      .pipe(filter(key => key === CoreContextDataNames.securityInfo))
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
    const securityInfo = this.appContextData.getContextData(CoreContextDataNames.securityInfo) as UiSecurityInfo;

    this._info = {
      currentUser: UiSecurityInfo.getUser(securityInfo),
      isItUser: this.securityService.isItUser(),
      isUserLogged: UiSecurityInfo.isLogged(securityInfo)
    };

    this.securityChange$.next(this._info);
  };
}
