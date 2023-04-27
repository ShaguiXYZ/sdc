import { Params, UrlSegment, UrlTree } from '@angular/router';
import { GenericDataInfo } from 'src/app/core/interfaces/dataInfo';
import { CacheData } from './cache-data.model';
import { ContextData } from './context-data';

export interface ICoreConfig {
  lang: string;
}

export interface ContextInfo {
  contextData: GenericDataInfo<ContextData>;
  cache: GenericDataInfo<CacheData>;
}

export enum CoreContextDataNames {
  securityInfo = 'security_info'
}

export interface ContextConfig {
  home: string;
  urls: RouteConfig;
  cache?: {
    schedulerPeriod?: number;
  };
}

export type RouteConfig = {
  [key: string]: UrlInfo;
};

export interface UrlInfo {
  resetContext: boolean;
  resetData?: string[];
  requiredData?: string[];
}

export interface RouterInfo {
  key: string;
  urlInfo: UrlInfo;
  tree: UrlTree;
  queryParams: Params;
  segments: UrlSegment[];
}
