import { Params, UrlSegment, UrlTree } from '@angular/router';
import { GenericDataInfo } from 'src/app/core/models';
import { CacheData } from './cache-data.model';
import { ContextData } from './context-data';

export interface CoreConfig {
  lang: string;
}

export interface ContextInfo {
  contextData: GenericDataInfo<ContextData>;
  cache: GenericDataInfo<CacheData>;
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
