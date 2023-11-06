import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { contextStorageID } from '../context-data';
import { UiContextDataService } from '../context-data/context-data.service';
import { HttpStatus, UiHttpService } from '../http';
import { CONTEXT_SECURITY_KEY } from './constants';
import { AppAuthorities, IAuthorityDTO, IAuthorityModel, ISecurityModel, ISessionModel, IUserDTO, IUserModel } from './models';
import { _console } from '../../lib';
import { SecurityError } from '../../errors';

@Injectable({
  providedIn: 'root'
})
export class UiSecurityService {
  private _urlSecurity = `${environment.securityUrl}/bff-security/api`;
  private signIn$: Subject<ISessionModel> = new Subject();

  constructor(private contextData: UiContextDataService, private http: UiHttpService) {}

  public get session(): ISessionModel {
    return this.securityInfo()?.session;
  }
  public set session(session: ISessionModel) {
    const securityInfo = { ...this.securityInfo(), session };

    this.contextData.set(CONTEXT_SECURITY_KEY, securityInfo, { persistent: true });
  }

  public get user(): IUserModel {
    return this.securityInfo()?.user;
  }
  public set user(user: IUserModel) {
    const securityInfo = this.securityInfo();
    if (securityInfo) {
      this.contextData.set(CONTEXT_SECURITY_KEY, { ...securityInfo, user }, { persistent: true });
    } else {
      throw new SecurityError('Valid token not returned');
    }
  }

  public logout() {
    sessionStorage.removeItem(contextStorageID);

    if (!this.securityInfo()) {
      this.http.put<ISessionModel, ISessionModel>(`${this._urlSecurity}/logout`).subscribe({
        next: event => {
          this.contextData.delete(CONTEXT_SECURITY_KEY);
          this.changeWindowLocationHref(environment.baseAuth);
        },
        error: err => {
          _console.log(err);

          if (err.status === HttpStatus.forbidden) {
            this.changeWindowLocationHref(environment.baseAuth);
          }
        }
      });
    } else {
      this.changeWindowLocationHref(environment.baseAuth);
    }
  }

  private changeWindowLocationHref(href: string) {
    globalThis.location.href = href;
  }

  public authUser(): Promise<IUserModel> {
    return firstValueFrom(this.http.get<IUserDTO>(`${this._urlSecurity}/authUser`).pipe(map(user => IUserModel.toModel(user as IUserDTO))));
  }

  public isBusinessUser = (): boolean =>
    this.securityInfo()
      ?.user?.authorities?.map(auth => auth.authority)
      .includes(AppAuthorities.business);

  public isItUser = (): boolean =>
    ISecurityModel.getUser(this.securityInfo())
      ?.authorities?.map(auth => auth.authority)
      .includes(AppAuthorities.it);

  public uidIsItUser = (uid: string): Promise<boolean> =>
    firstValueFrom(
      this.getAuthoritiesByUID(uid).pipe(map(authorities => authorities.map(auth => auth.authority).includes(AppAuthorities.it)))
    );

  public uidSameProfile = (authority: string): boolean =>
    ISecurityModel.getUser(this.securityInfo()).authorities?.some(auth => auth.authority === authority);

  public onSignIn(): Observable<ISessionModel> {
    return this.signIn$.asObservable();
  }

  private securityInfo(): ISecurityModel {
    return this.contextData.get(CONTEXT_SECURITY_KEY) as ISecurityModel;
  }

  private getAuthoritiesByUID(uid: string): Observable<IAuthorityModel[]> {
    return this.http.get<IAuthorityDTO[]>(`${this._urlSecurity}/authorities/${uid}`).pipe(
      catchError((err, caught: Observable<any>) => {
        _console.log(err);
        return caught;
      }),
      map(auth => auth.map((value: IAuthorityDTO) => IAuthorityModel.toModel(value)))
    );
  }
}
