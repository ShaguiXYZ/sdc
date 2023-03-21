/* eslint-disable max-classes-per-file */
import { Inject, Injectable } from '@angular/core';
import { NavigationEnd, PRIMARY_OUTLET, Router, UrlTree } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { APP_NAME } from 'src/app/core/constants/app.constants';
import { DataInfo } from 'src/app/core/interfaces/dataInfo';
import { deepCopy } from '../lib';
import { ContextConfig, ContextInfo, CoreContextDataNames, NX_CONTEX_CONFIG, RouterInfo, UrlInfo } from '../models/context/contex.model';

export const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

/**
 * Data persistence service between screens
 */
@Injectable({
  providedIn: 'root'
})
export class UiAppContextDataService {
  private contextStorage: ContextInfo;
  private subject$: Subject<string>;

  constructor(@Inject(NX_CONTEX_CONFIG) private config: ContextConfig = { home: '', urls: {} }, private router: Router) {
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
  public setContextData(key: string, data: any): void {
    this.contextStorage.contextData[key] = data;
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

      console.log(urlInfo);

      if (urlInfo?.resetContext) {
        const coreValues = Object.values(CoreContextDataNames) as string[];
        const values = Object.keys(this.contextStorage.contextData);
        values.filter(value => coreValues.indexOf(value) === 0).forEach(value => this.delete(value));
      } else {
        urlInfo.resetData?.forEach(value => this.delete(value));
      }
    });
  }

  private urlInfoBykey = (key: string): UrlInfo => this.config.urls[key];

  private routerData(router: Router): RouterInfo {
    const tree: UrlTree = router.parseUrl(router.url);
    const urlSegments = tree.root.children[PRIMARY_OUTLET] ? tree.root.children[PRIMARY_OUTLET].segments || [] : [];
    const key = tree.root.children.hasOwnProperty(PRIMARY_OUTLET) ? urlSegments[0].path : this.config.home;

    return {
      key,
      urlInfo: this.urlInfoBykey(key),
      tree,
      queryParams: deepCopy(tree.queryParams),
      segments: urlSegments
    };
  }
}
