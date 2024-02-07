import { Injectable } from '@angular/core';
import { Observable, Subject, firstValueFrom } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { SecurityError } from '../../errors';
import { ContextDataService } from '../context-data/context-data.service';
import { HttpService } from '../http';
import { CONTEXT_SECURITY_KEY } from './constants';
import { ISecurityModel, ISessionDTO, ISessionModel, IUserDTO, IUserModel } from './models';

import packageInfo from 'package.json';
import { contextStorageID } from '../context-data';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {
  private _urlSecurity = `${environment.securityUrl}/api`;
  private signIn$: Subject<ISessionModel> = new Subject();

  constructor(
    private readonly contextDataService: ContextDataService,
    private readonly http: HttpService
  ) {}

  public get session(): ISessionModel {
    return this.securityInfo()?.session;
  }
  public set session(session: ISessionModel) {
    const securityInfo = { ...this.securityInfo(), session };

    this.contextDataService.set(CONTEXT_SECURITY_KEY, securityInfo, { persistent: true });
  }

  public get user(): IUserModel {
    return this.securityInfo()?.user;
  }
  public set user(user: IUserModel) {
    const securityInfo = this.securityInfo();

    if (securityInfo) {
      this.contextDataService.set(CONTEXT_SECURITY_KEY, { ...securityInfo, user }, { persistent: true });
    } else {
      throw new SecurityError('Not valid token returned');
    }
  }

  public onSignIn(): Observable<ISessionModel> {
    return this.signIn$.asObservable();
  }

  public login(loginData: { userName: string; password: string }): Promise<ISessionModel> {
    return firstValueFrom(
      this.http
        .post<ISessionDTO, { resource: string; userName: string; password: string }>(`${this._urlSecurity}/login`, {
          ...loginData,
          resource: packageInfo.name
        })
        .pipe(
          map(session => {
            this.session = ISessionModel.fromDTO(session as ISessionDTO);
            return this.session;
          }),
          tap(session => this.signIn$.next(session))
        )
    );
  }

  public logout(): Promise<void> {
    sessionStorage.removeItem(contextStorageID);

    if (!this.securityInfo()) {
      return firstValueFrom(
        this.http._put<ISessionModel>(`${this._urlSecurity}/logout`).pipe(
          map(() => {
            this.contextDataService.delete(CONTEXT_SECURITY_KEY);
          })
        )
      );
    }

    return Promise.resolve();
  }

  public authUser(): Promise<IUserModel> {
    return firstValueFrom(this.http.get<IUserDTO>(`${this._urlSecurity}/authUser`).pipe(map(user => IUserModel.fromDTO(user as IUserDTO))));
  }

  private securityInfo(): ISecurityModel {
    return this.contextDataService.get<ISecurityModel>(CONTEXT_SECURITY_KEY);
  }
}
