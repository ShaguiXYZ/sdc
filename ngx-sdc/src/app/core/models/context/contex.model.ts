import { InjectionToken } from '@angular/core';
import { Params, UrlSegment, UrlTree } from '@angular/router';
import { DataInfo } from '../../interfaces/dataInfo';

export interface ContextInfo {
  contextData: DataInfo;
  cache: DataInfo;
}

export enum CoreContextDataNames {
  appConfig = 'app_Config',
  securityInfo = 'security_info',
}

export const NX_CONTEX_CONFIG = new InjectionToken<ContextConfig>(
  'NX_CONTEX_CONFIG'
);

export interface ContextConfig {
  home: string;
  urls: RouteConfig;
};

export type RouteConfig = {
  [key: string]: UrlInfo;
};

export interface UrlInfo {
  resetContext: boolean;
  resetData?: string[];
};

export interface RouterInfo {
  key: string;
  urlInfo: UrlInfo;
  tree: UrlTree;
  queryParams: Params;
  segments: UrlSegment[];
};
