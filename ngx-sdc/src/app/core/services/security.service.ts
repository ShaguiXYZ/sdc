import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpStatus } from 'src/app/core/constants/app.constants';
import { environment } from 'src/environments/environment';
import { CoreContextDataNames } from '../models/context/contex.model';
import { AppAuthorities, IAuthorityDTO, IAuthorityModel, ISessionModel, IUserDTO, IUserModel } from '../models/security';
import { UiSecurityInfo } from '../models/security/security.model';
import { contextStorageID, UiAppContextDataService } from './context-data.service';
import { UiHttpService } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class UiSecurityService {
  private _urlSecurity = `${environment.securityUrl}/bff-security/api`;
  private signIn$: Subject<ISessionModel> = new Subject();

  constructor(private contextData: UiAppContextDataService, private http: UiHttpService) {}

  public get session(): ISessionModel {
    return this.securityInfo()?.session;
  }
  public set session(session: ISessionModel) {
    const securityInfo = this.securityInfo();

    if (securityInfo) {
      this.contextData.setContextData(CoreContextDataNames.securityInfo, { ...securityInfo, session });
    } else {
      this.contextData.setContextData(CoreContextDataNames.securityInfo, { session });
    }
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  public get user(): IUserModel {
    return this.securityInfo()?.user;
  }
  public set user(user: IUserModel) {
    const securityInfo = this.securityInfo();

    if (securityInfo) {
      this.contextData.setContextData(CoreContextDataNames.securityInfo, { ...securityInfo, user });
    } else {
      throw new Error('Valid token not returned');
    }
  }

  public logout() {
    sessionStorage.removeItem(contextStorageID);

    if (!this.securityInfo()) {
      this.http.put<ISessionModel, ISessionModel>(`${this._urlSecurity}/logout`).subscribe({
        next: event => {
          this.contextData.delete(CoreContextDataNames.securityInfo);
          window.location.href = environment.baseAuth;
        },
        error: err => {
          console.log(err);

          if (err.status === HttpStatus.forbidden) {
            window.location.href = environment.baseAuth;
          }
        }
      });
    } else {
      window.location.href = environment.baseAuth;
    }
  }

  public authUser(): Promise<IUserModel> {
    return firstValueFrom(this.http.get<IUserDTO>(`${this._urlSecurity}/authUser`).pipe(map(user => IUserModel.toModel(user as IUserDTO))));
  }

  public isBusinessUser = (): boolean =>
    this.securityInfo()
      ?.user?.authorities?.map(auth => auth.authority)
      .includes(AppAuthorities.business);

  public isItUser = (): boolean =>
    UiSecurityInfo.getUser(this.securityInfo())
      ?.authorities?.map(auth => auth.authority)
      .includes(AppAuthorities.it);

  public uidIsItUser = (uid: string): Promise<boolean> =>
    firstValueFrom(
      this.getAuthoritiesByUID(uid).pipe(map(authorities => authorities.map(auth => auth.authority).includes(AppAuthorities.it)))
    );

  public uidSameProfile = (authority: string): boolean =>
    UiSecurityInfo.getUser(this.securityInfo()).authorities?.some(auth => auth.authority === authority);

  public onSignIn(): Observable<ISessionModel> {
    return this.signIn$.asObservable();
  }

  private securityInfo(): UiSecurityInfo {
    return this.contextData.getContextData(CoreContextDataNames.securityInfo) as UiSecurityInfo;
  }

  private getAuthoritiesByUID(uid: string): Observable<IAuthorityModel[]> {
    return this.http.get<IAuthorityDTO[]>(`${this._urlSecurity}/authorities/${uid}`).pipe(
      catchError((err, caught: Observable<any>) => {
        console.log(err);
        return caught;
      }),
      map(auth => auth.map((value: IAuthorityDTO) => IAuthorityModel.toModel(value)))
    );
  }
}
