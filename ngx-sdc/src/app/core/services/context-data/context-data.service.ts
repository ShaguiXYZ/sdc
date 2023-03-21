/* eslint-disable max-classes-per-file */
import { Inject, Injectable } from '@angular/core';
import { NavigationEnd, PRIMARY_OUTLET, Router, UrlTree } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { APP_NAME, DEFAULT_LANGUAGE } from 'src/app/core/constants/app.constants';
import { DataInfo } from 'src/app/core/interfaces/dataInfo';
import { deepCopy, UniqueIds } from '../../lib';
import {
  ContextConfig,
  ContextData,
  ContextInfo,
  IContextDataConfigurtion,
  ICoreConfig,
  NX_CONTEX_CONFIG,
  RouterInfo,
  UrlInfo
} from './models';

export const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

/**
 * Data persistence service between screens
 */
@Injectable({
  providedIn: 'root'
})
export class UiAppContextDataService {
  private _contextConfig: ContextConfig;
  private _configKey = `_${UniqueIds.next()}_`;
  private contextStorage: ContextInfo;
  private subject$: Subject<string>;

  constructor(
    @Inject(NX_CONTEX_CONFIG) contextConfig: ContextConfig = { lang: DEFAULT_LANGUAGE, home: '', urls: {} },
    private router: Router
  ) {
    this.contextStorage = {
      contextData: {},
      cache: {}
    };

    this.subject$ = new Subject<string>();
    this._contextConfig = { ...{ lang: DEFAULT_LANGUAGE, home: '', urls: {} }, ...contextConfig };

    this.sessionControl();
    this.contextStorage.cache[this._configKey] = { lang: this._contextConfig.lang };
  }

  public contextDataServiceConfiguration(): ICoreConfig {
    return deepCopy(this.contextStorage.cache[this._configKey]);
  }

  /**
   * Public context data
   */
  public get cache(): DataInfo {
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
  public getContextData(key?: string): any {
    if (key) {
      return deepCopy(this.contextStorage.contextData[key]?.data);
    } else {
      const contextDataValues: DataInfo = {};
      Object.keys(this.contextStorage.contextData).forEach(
        contextKey => (contextDataValues[contextKey] = this.contextStorage.contextData[contextKey].data)
      );
      return contextDataValues;
    }
  }

  /**
   * set context data to the storage
   *
   * @param key Key of the variable in context
   * @param data data to save in the storage
   */
  public setContextData(key: string, data: any, configuration: IContextDataConfigurtion = {}): void {
    this.addContextData(key, data, configuration);
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

  private addContextData(key: string, data: any, configuration: IContextDataConfigurtion = {}, isCore: boolean = false): ContextData {
    const contextData = new ContextData(data, configuration || {}, isCore);
    this.contextStorage.contextData[key] = contextData;

    return contextData;
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
      const urlInfo: UrlInfo = this.routerData(this.router).urlInfo;

      if (urlInfo?.resetContext) {
        const keys = Object.keys(this.contextStorage.contextData);

        keys
          .filter(key => !this.contextStorage.contextData[key].protected())
          .forEach(key => this.delete(key));
      } else {
        urlInfo.resetData?.forEach(value => this.delete(value));
      }

      console.log('Context Storage', this.contextStorage);

    });
  }

  private urlInfoBykey = (key: string): UrlInfo => this._contextConfig.urls[key];

  private routerData(router: Router): RouterInfo {
    const tree: UrlTree = router.parseUrl(router.url);
    const urlSegments = tree.root.children[PRIMARY_OUTLET] ? tree.root.children[PRIMARY_OUTLET].segments || [] : [];
    const key = tree.root.children.hasOwnProperty(PRIMARY_OUTLET) ? urlSegments[0].path : this._contextConfig.home;

    return {
      key,
      urlInfo: this.urlInfoBykey(key),
      tree,
      queryParams: deepCopy(tree.queryParams),
      segments: urlSegments
    };
  }
}
