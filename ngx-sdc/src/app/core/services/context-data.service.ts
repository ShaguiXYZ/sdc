/* eslint-disable max-classes-per-file */
import { Injectable } from '@angular/core';
import { NavigationEnd, Router, RouterEvent } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { APP_NAME } from 'src/app/shared/config/app.constants';
import { AppConfig, ContextDataNames, ContextInfo } from 'src/app/shared/config/contextInfo';
import { routerData, UrlInfo } from 'src/app/shared/config/routing';
import { DataInfo, GenericDataInfo } from 'src/app/shared/interfaces/dataInfo';
import { UiSecurityInfo } from '../models/security/security.model';

export const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

interface ContextDataInfo {
  hasPersistence: boolean;
}

class UiContextDataDefinition {
  public static readonly dataProperties: GenericDataInfo<ContextDataInfo> = {
    [ContextDataNames.appConfig]: { hasPersistence: true }
  };
}

/**
 * Data persistence service between screens
 */
@Injectable({
  providedIn: 'root'
})
export class UiAppContextData {
  private subject$: Subject<string>;
  private contextStorage: ContextInfo;

  constructor(private router: Router) {
    this.subject$ = new Subject<string>();

    this.contextStorage = {
      contextData: {},
      cache: {}
    };

    this.sessionControl();
  }

  /**
   * Public context data
   */
  public get cache(): DataInfo {
    return this.contextStorage.cache;
  }

  public get appConfig(): AppConfig {
    return this.getContextData(ContextDataNames.appConfig);
  }
  public set appConfig(value: AppConfig) {
    this.setContextData(ContextDataNames.appConfig, value);
  }

  public get securityInfo(): UiSecurityInfo {
    return this.getContextData(ContextDataNames.securityInfo);
  }
  public set securityInfo(value: Partial<UiSecurityInfo>) {
    this.setContextData(ContextDataNames.securityInfo, value);
  }

  public onDataChange(): Observable<string> {
    return this.subject$.asObservable();
  }

  /**
   * get contest data from the context storage
   *
   * @param key Key of the variable in context
   */
  public getContextData(key?: ContextDataNames): any {
    if (key) {
      return this.contextStorage.contextData[key];
    } else {
      return this.contextStorage.contextData;
    }
  }

  /**
   * set context data to the storage
   *
   * @param key Key of the variable in context
   * @param data data to save in the storage
   */
  public setContextData(key: ContextDataNames, data: any): void {
    this.contextStorage.contextData[key] = data;
    this.subject$.next(key);
  }

  /**
   * Delete data from the storage
   *
   * @param key Key of the variable in context
   */
  public delete(key: ContextDataNames): void {
    delete this.contextStorage.contextData[key];
  }

  private sessionControl(): void {
    this.refreshPageControl();
    this.sessionDataControl();

    if (this.router) {
      this.controlData();
    }
  }

  // Load session storage (F5)
  private refreshPageControl = (): void => {
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem(contextStorageID, JSON.stringify(this.contextStorage.contextData));
    });
  };

  // Recover data form storage
  private sessionDataControl = (): void => {
    const sessionData = sessionStorage.getItem(contextStorageID);

    if (sessionData) {
      this.contextStorage.contextData = JSON.parse(sessionData);
      sessionStorage.removeItem(contextStorageID);
    }
  };

  /**
   * Control the persistence of the data in the storage
   */
  private controlData(): void {
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
      const urlInfo: UrlInfo = routerData(this.router).urlInfo;

      if (urlInfo?.resetContext) {
        const values = Object.values(ContextDataNames);

        values.forEach((value: ContextDataNames) => {
          if (UiContextDataDefinition.dataProperties[value] && !UiContextDataDefinition.dataProperties[value].hasPersistence) {
            this.delete(value);
          }
        });
      } else {
        urlInfo.resetData?.forEach((data: ContextDataNames) => {
          this.delete(data);
        });
      }
    });
  }
}
