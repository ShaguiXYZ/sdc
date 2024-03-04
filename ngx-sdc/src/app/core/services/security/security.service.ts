import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { SecurityError } from '../../errors';
import { ContextDataService } from '../context-data/context-data.service';
import { HttpService, HttpStatus } from '../http';
import { CONTEXT_SECURITY_KEY } from './constants';
import { ISecurityModel, ISessionDTO, ISessionModel, IUserDTO, IUserModel } from './models';

import packageInfo from 'package.json';
import { contextStorageID } from '../context-data';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {
  private _urlSecurity = `${environment.securityUrl}/api`;
  private _SignInSignOut$: Subject<ISecurityModel | undefined> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly http: HttpService
  ) {}

  public get session(): ISessionModel {
    return this.getSecurityInfo()?.session;
  }
  public set session(session: ISessionModel) {
    this.setSecurityInfo({ ...this.getSecurityInfo(), session });
  }

  public get user(): IUserModel {
    return this.getSecurityInfo()?.user;
  }
  public set user(user: IUserModel) {
    const securityInfo = this.getSecurityInfo();

    if (securityInfo) {
      this.setSecurityInfo({ ...securityInfo, user });
    } else {
      throw new SecurityError('Not valid token returned');
    }
  }

  public onSignInOut(): Observable<ISecurityModel | undefined> {
    return this._SignInSignOut$.asObservable();
  }

  public forceLogout = (): void => {
    this.removeSecurityInfo();
    this._SignInSignOut$.next(undefined);
  };

  public async login(loginData: { userName: string; password: string }): Promise<ISessionModel> {
    await firstValueFrom(
      this.http
        .post<ISessionDTO, { resource: string; userName: string; password: string }>(`${this._urlSecurity}/login`, {
          ...loginData,
          resource: packageInfo.name
        })
        .pipe(
          map(session => {
            this.session = ISessionModel.fromDTO(session as ISessionDTO);
            this.authUser().then(user => (this.user = user));

            return this.session;
          }),
          catchError(() => {
            this.removeSecurityInfo();
            return Promise.reject(new SecurityError('Invalid credentials'));
          })
        )
    );

    return this.session;
  }

  public logout(): Promise<void> {
    if (this.getSecurityInfo()) {
      return firstValueFrom(
        this.http
          ._put<ISessionDTO>(`${this._urlSecurity}/logout`, undefined, {
            responseStatusMessage: {
              [HttpStatus.unauthorized]: { fn: this.forceLogout }
            }
          })
          .pipe(
            tap(() => this.removeSecurityInfo()),
            map(() => this._SignInSignOut$.next(undefined))
          )
      );
    }

    return Promise.resolve();
  }

  public authUser(): Promise<IUserModel> {
    return firstValueFrom(this.http.get<IUserDTO>(`${this._urlSecurity}/authUser`).pipe(map(user => IUserModel.fromDTO(user as IUserDTO))));
  }

  private getSecurityInfo(): ISecurityModel {
    const data = localStorage.getItem(contextStorageID);

    return data && JSON.parse(data); // this.contextDataService.get<ISecurityModel>(CONTEXT_SECURITY_KEY);
  }

  private setSecurityInfo(securityInfo: ISecurityModel): void {
    localStorage.setItem(contextStorageID, JSON.stringify(securityInfo));
    this.contextDataService.set<ISecurityModel>(CONTEXT_SECURITY_KEY, securityInfo, { persistent: true });

    this._SignInSignOut$.next(securityInfo);
  }

  private removeSecurityInfo = (): void => {
    localStorage.removeItem(contextStorageID);
    this.contextDataService.delete(CONTEXT_SECURITY_KEY);
  };
}
