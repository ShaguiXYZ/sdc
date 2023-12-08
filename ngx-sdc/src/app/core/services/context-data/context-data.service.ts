import { Inject, Injectable, Optional } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { ContextDataError } from '../../errors';
import { _console, deepCopy } from '../../lib';
import { DataInfo } from '../../models';
import { NX_CONTEX_CONFIG, contextStorageID } from './constatnts';
import { routerData } from './lib';
import { CacheData, ContextConfig, ContextData, ContextInfo, IContextData, IContextDataConfigurtion, UrlInfo } from './models';

/**
 * Data persistence service between screens
 */
@Injectable({
  providedIn: 'root'
})
export class ContextDataService {
  private _contextConfig: ContextConfig;
  private contextStorage: ContextInfo;
  private subject$: Subject<string>;

  /**
   * Constructor
   *
   * @param contextConfig
   * @param router
   */
  constructor(
    @Optional() @Inject(NX_CONTEX_CONFIG) contextConfig: ContextConfig,
    private readonly router: Router
  ) {
    this.contextStorage = {
      contextData: {},
      cache: {}
    };

    this.subject$ = new Subject<string>();
    this._contextConfig = { ...{ home: '', urls: {} }, ...contextConfig };

    this.sessionControl();
  }

  public get cache(): DataInfo<CacheData> {
    return this.contextStorage.cache;
  }

  public onDataChange(): Observable<string> {
    return this.subject$.asObservable();
  }

  /**
   * get contest data from the context storage
   *
   * @param key Key of the variable in context
   */
  public get<T = any>(key: string): T {
    const contextData = this.contextStorage.contextData[key];

    return contextData?.configuration.referenced
      ? this.contextStorage.contextData[key]?.data
      : deepCopy(this.contextStorage.contextData[key]?.data);
  }

  public getConfiguration(key: string): IContextDataConfigurtion {
    return { ...this.contextStorage.contextData[key].configuration };
  }

  /**
   * set context data to the storage
   *
   * @param key Key of the variable in context
   * @param data data to save in the storage
   */
  public set<T = any>(key: string, data: T, configuration?: IContextDataConfigurtion): void {
    const contextDataValue = this.contextStorage.contextData[key];
    const config = configuration ?? contextDataValue?.configuration ?? { persistent: false };

    if (config.readonly) {
      throw new ContextDataError(`${key} is read only`);
    }

    this.addContextData(key, data, config);

    _console.log(`${key} added`, this.contextStorage.contextData);

    this.subject$.next(key);
  }

  public patch<T = any>(key: string, data: Partial<T>): void {
    const contextDataValue = this.contextStorage.contextData[key];

    if (contextDataValue?.configuration.readonly) {
      throw new ContextDataError(`${key} is read only`);
    }

    this.addContextData(key, { ...contextDataValue?.data, ...data }, contextDataValue?.configuration);

    _console.log(`${key} patched`, this.contextStorage.contextData);

    this.subject$.next(key);
  }

  /**
   * Delete data from the storage
   *
   * @param key Key of the variable in context
   */
  public delete(key: string): void {
    delete this.contextStorage.contextData[key];
  }

  private addContextData<T = any>(key: string, data: T, configuration: IContextDataConfigurtion = {}): ContextData {
    const contextData = new ContextData(data, configuration || {});
    this.contextStorage.contextData[key] = contextData;

    return contextData;
  }

  /**
   * Control session storage (F5)
   * When the page is refreshed, the data is saved in the session storage
   */
  private sessionControl(): void {
    this.refreshPageControl();
    this.sessionDataControl();

    if (this.router) {
      this.controlData();
    }
  }

  /**
   * Save data to session storage (F5)
   * When the page is refreshed, the data is saved in the session storage
   */
  private refreshPageControl = (): void => {
    globalThis.addEventListener('beforeunload', () => {
      const sessionData: DataInfo<IContextData> = {};

      Object.entries(this.contextStorage.contextData).forEach(
        ([key, value]) =>
          (sessionData[key] = {
            data: value.data,
            configuration: value.configuration
          })
      );

      sessionStorage.setItem(contextStorageID, JSON.stringify(sessionData));
    });
  };

  /**
   * Load session storage (F5)
   * If there is data in the session storage, it is loaded into the context storage
   * and deleted from the session storage
   */
  private sessionDataControl = (): void => {
    const sessionData = sessionStorage.getItem(contextStorageID);

    if (sessionData) {
      const data: DataInfo<IContextData> = JSON.parse(sessionData);

      Object.entries(data).forEach(([key, value]) => this.addContextData(key, value.data, value.configuration));

      sessionStorage.removeItem(contextStorageID);
    }
  };

  /**
   * Control data according to the url
   * If the url is resetContext, all data is deleted
   */
  private controlData(): void {
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
      const urlInfo: UrlInfo = routerData(this.router, this._contextConfig).urlInfo;

      if (urlInfo?.resetContext) {
        const keys = Object.keys(this.contextStorage.contextData);

        keys.filter(key => !this.contextStorage.contextData[key].protected()).forEach(key => this.delete(key));
      } else {
        urlInfo?.resetData?.forEach(value => this.delete(value));
      }
    });
  }
}
