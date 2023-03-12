import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom, Observable, Subject } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { HttpStatus } from 'src/app/shared/config/app.constants';
import { ContextDataNames } from 'src/app/shared/config/contextInfo';
import { environment } from 'src/environments/environment';
import { AppAuthorities, IAuthorityDTO, IAuthorityModel, ISessionModel, IUserDTO, IUserModel } from '../models/security';
import { UiSecurityInfo } from '../models/security/security.model';
import { contextStorageID, UiAppContextData } from './context-data.service';
import { UiHttpHelper } from './http.service';

@Injectable({
  providedIn: 'root'
})
export class UiSecurityService {
  private _urlSecurity = `${environment.securityUrl}/bff-security/api`;
  private signIn$: Subject<ISessionModel> = new Subject();

  constructor(private contextData: UiAppContextData, private http: UiHttpHelper) {}

  public get session(): ISessionModel {
    return this.contextData.securityInfo?.session;
  }
  public set session(session: ISessionModel) {
    if (this.contextData.securityInfo) {
      this.contextData.securityInfo = { ...this.contextData.securityInfo, session };
    } else {
      this.contextData.securityInfo = { session };
    }
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  public get user(): IUserModel {
    return this.contextData.securityInfo?.user;
  }
  public set user(user: IUserModel) {
    if (this.contextData.securityInfo) {
      this.contextData.securityInfo = { ...this.contextData.securityInfo, user };
    } else {
      throw new Error('Valid token not returned');
    }
  }

  public logout() {
    sessionStorage.removeItem(contextStorageID);

    if (!this.contextData.securityInfo) {
      this.http.put<ISessionModel, ISessionModel>(`${this._urlSecurity}/logout`).subscribe({
        next: event => {
          this.contextData.delete(ContextDataNames.securityInfo);
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
    this.contextData.securityInfo?.user?.authorities?.map(auth => auth.authority).includes(AppAuthorities.business);

  public isItUser = (): boolean =>
    UiSecurityInfo.getUser(this.contextData.securityInfo)
      ?.authorities?.map(auth => auth.authority)
      .includes(AppAuthorities.it);

  public uidIsItUser = (uid: string): Promise<boolean> =>
    firstValueFrom(
      this.getAuthoritiesByUID(uid).pipe(map(authorities => authorities.map(auth => auth.authority).includes(AppAuthorities.it)))
    );

  public uidSameProfile = (authority: string): boolean =>
    UiSecurityInfo.getUser(this.contextData.securityInfo).authorities?.some(auth => auth.authority === authority);

  public onSignIn(): Observable<ISessionModel> {
    return this.signIn$.asObservable();
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
