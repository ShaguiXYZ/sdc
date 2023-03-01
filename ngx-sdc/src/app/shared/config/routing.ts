import { Params, PRIMARY_OUTLET, Router, UrlSegment, UrlTree } from '@angular/router';
import { deepCopy } from 'src/app/core/lib/object-utils';
import { ContextDataNames } from './contextInfo';

// eslint-disable-next-line no-shadow
export enum AppUrls {
  root = '',
  home = 'home',
  test = 'test'
}

type RouteConfig<T> = {
  [key in AppUrls]: T;
};

export interface UrlInfo {
  resetContext: boolean;
  resetData?: ContextDataNames[];
}

export class UrlsDefinition {
  public static readonly urls: RouteConfig<UrlInfo> = {
    [AppUrls.root]: { resetContext: true },
    [AppUrls.home]: { resetContext: true },
    [AppUrls.test]: { resetContext: false, resetData: [] }
  };
}

export const urlInfoBykey = (key: AppUrls): UrlInfo => UrlsDefinition.urls[key];

export interface RouterInfo {
  key: AppUrls;
  urlInfo: UrlInfo;
  tree: UrlTree;
  queryParams: Params;
  segments: UrlSegment[];
}

const initialUrl = AppUrls.home;

export const routerData = (router: Router): RouterInfo => {
  const tree: UrlTree = router.parseUrl(router.url);
  const urlSegments = tree.root.children[PRIMARY_OUTLET] ? tree.root.children[PRIMARY_OUTLET].segments || [] : [];

  const key = tree.root.children.hasOwnProperty(PRIMARY_OUTLET) ? (urlSegments[0].path as AppUrls) : initialUrl;

  return {
    key,
    urlInfo: urlInfoBykey(key),
    tree,
    queryParams: deepCopy(tree.queryParams),
    segments: urlSegments
  };
};
