import { Inject, Injectable, Optional } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { APP_NAME } from 'src/app/core/constants/app.constants';
import { DataInfo, GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { deepCopy } from '../../lib';
import { routerData } from './lib';
import { CacheData, ContextConfig, ContextData, ContextInfo, IContextData, IContextDataConfigurtion, NX_CONTEX_CONFIG, UrlInfo } from './models';

export const contextStorageID = `CTX_${APP_NAME.toUpperCase()}`; // Key for data how is saved in session

/**
 * Data persistence service between screens
 */
@Injectable({
  providedIn: 'root'
})
export class UiContextDataService {
  private _contextConfig: ContextConfig;
  private contextStorage: ContextInfo;
  private subject$: Subject<string>;

  constructor(@Optional() @Inject(NX_CONTEX_CONFIG) contextConfig: ContextConfig, private router: Router) {
    this.contextStorage = {
      contextData: {},
      cache: {}
    };

    this.subject$ = new Subject<string>();
    this._contextConfig = { ...{ home: '', urls: {} }, ...contextConfig };

    this.sessionControl();
  }

  /**
   * Public context data
   */
  public get cache(): GenericDataInfo<CacheData> {
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
  public get(key?: string): any {
    if (key) {
      const contextData = this.contextStorage.contextData[key];

      if (contextData?.configuration.referenced) {
        return this.contextStorage.contextData[key]?.data;
      } else {
        return deepCopy(this.contextStorage.contextData[key]?.data);
      }
    } else {
      const contextDataValues: DataInfo = {};

      Object.keys(this.contextStorage.contextData).forEach(
        contextKey => (contextDataValues[contextKey] = deepCopy(this.contextStorage.contextData[contextKey].data))
      );

      return contextDataValues;
    }
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
  public set(key: string, data: any, configuration: IContextDataConfigurtion = { persistent: false }): void {
    const contextDataValue = this.contextStorage.contextData[key];

    if (contextDataValue && contextDataValue.configuration.readonly) {
      throw new Error(`${key} is read only`);
    }

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

  private addContextData(key: string, data: any, configuration: IContextDataConfigurtion = {}): ContextData {
    const contextData = new ContextData(data, configuration || {});
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
      const sessionData: GenericDataInfo<IContextData> = {};

      Object.keys(this.contextStorage.contextData).forEach(key => {
        sessionData[key] = {
          data: this.contextStorage.contextData[key].data,
          configuration: this.contextStorage.contextData[key].configuration
        };
      });

      sessionStorage.setItem(contextStorageID, JSON.stringify(sessionData));
    });
  };

  // Recover data form storage
  private sessionDataControl = (): void => {
    const sessionData = sessionStorage.getItem(contextStorageID);

    if (sessionData) {
      const data: GenericDataInfo<IContextData> = JSON.parse(sessionData);

      Object.keys(data).forEach(key => {
        this.addContextData(key, data[key].data, data[key].configuration);
      });

      sessionStorage.removeItem(contextStorageID);
    }
  };

  /**
   * Control the persistence of the data in the storage
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
