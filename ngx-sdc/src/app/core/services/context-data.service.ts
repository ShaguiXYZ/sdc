import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { APP_NAME } from 'src/app/shared/config/app.constants';
import { AppConfig, ContextDataNames, ContextInfo } from 'src/app/shared/config/contextInfo';
import { DataInfo } from 'src/app/shared/interfaces/dataInfo';

const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

interface ContextDataInfo {
  hasPersistence: boolean;
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

  constructor() {
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
  public setContextData(key: ContextDataNames, data: any) {
    this.contextStorage.contextData[key] = data;
    this.subject$.next(key);
  }

  /**
   * Delete data from the storage
   *
   * @param key Key of the variable in context
   */
  public delete(key: ContextDataNames) {
    delete this.contextStorage.contextData[key];
  }

  private sessionControl(): void {
    this.refreshPageControl();
    this.sessionDataControl();
  }

  // Load session storage (F5)
  private refreshPageControl = () => {
    window.addEventListener('beforeunload', (event: Event) => {
      sessionStorage.setItem(contextStorageID, JSON.stringify(this.contextStorage.contextData));
    });
  };

  // Recover data form storage
  private sessionDataControl = () => {
    const sessionData = sessionStorage.getItem(contextStorageID);

    if (sessionData) {
      this.contextStorage.contextData = JSON.parse(sessionData);
      sessionStorage.removeItem(contextStorageID);
    }
  };
}
