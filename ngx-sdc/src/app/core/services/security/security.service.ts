import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { SecurityError } from '../../errors';
import { CONTEXT_SECURITY_KEY } from './constants';
import { ISecurityModel, ISessionDTO, ISessionModel, IUserDTO, IUserModel } from './models';

import { HttpStatusCode } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { ContextDataService, HttpService } from '@shagui/ng-shagui/core';
import packageInfo from 'package.json';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {
  private _urlSecurity = `${environment.securityUrl}/api/security`;
  private _SignInSignOut$: Subject<ISecurityModel | undefined> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly http: HttpService,
    private readonly translate: TranslateService
  ) {}

  public get session(): ISessionModel {
    return this.getSecurityInfo()?.session;
  }

  public get user(): IUserModel {
    return this.getSecurityInfo()?.user;
  }

  public set session(session: ISessionModel) {
    this.setSecurityInfo({ ...this.getSecurityInfo(), session });
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
        .post<{ resource: string; userName: string; password: string }, ISessionDTO>(
          `${this._urlSecurity}/login`,
          {
            ...loginData,
            resource: packageInfo.name
          },
          {
            procesingMessage: { text: this.translate.instant('Notifications.LoginProcessing') },
            successMessage: {
              title: this.translate.instant('Notifications.LoginSuccess'),
              text: this.translate.instant('Notifications.LoginSuccess')
            }
          }
        )
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
          .put<ISessionDTO>(`${this._urlSecurity}/logout`, undefined, {
            responseStatusMessage: {
              [HttpStatusCode.Unauthorized]: { fn: this.forceLogout }
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
    const data = localStorage.getItem(CONTEXT_SECURITY_KEY);

    return data && JSON.parse(data); // this.contextDataService.get<ISecurityModel>(CONTEXT_SECURITY_KEY);
  }

  private setSecurityInfo(securityInfo: ISecurityModel): void {
    localStorage.setItem(CONTEXT_SECURITY_KEY, JSON.stringify(securityInfo));
    this.contextDataService.set<ISecurityModel>(CONTEXT_SECURITY_KEY, securityInfo, { persistent: true });

    this._SignInSignOut$.next(securityInfo);
  }

  private removeSecurityInfo = (): void => {
    localStorage.removeItem(CONTEXT_SECURITY_KEY);
    this.contextDataService.delete(CONTEXT_SECURITY_KEY);
  };
}
