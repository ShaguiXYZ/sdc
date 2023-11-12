import { PRIMARY_OUTLET, Route, Router, UrlTree } from '@angular/router';
import { deepCopy, hasValue } from 'src/app/core/lib';
import { ContextValidGuard } from '../guard';
import { ContextConfig, RouterInfo, UrlInfo } from '../models';

export const urlInfoBykey = (key: string, contextConfig: ContextConfig): UrlInfo => contextConfig.urls[key];

export const routerData = (router: Router, contextConfig: ContextConfig): RouterInfo => {
  const tree: UrlTree = router.parseUrl(router.url);
  const urlSegments = tree.root.children[PRIMARY_OUTLET] ? tree.root.children[PRIMARY_OUTLET].segments || [] : [];
  const key = tree.root.children.hasOwnProperty(PRIMARY_OUTLET) ? urlSegments[0].path : contextConfig.home;

  return {
    key,
    urlInfo: urlInfoBykey(key, contextConfig),
    tree,
    queryParams: deepCopy(tree.queryParams),
    segments: urlSegments
  };
};

export const configContextRoutes = (routes: Route[]): Route[] => {
  routes.push({
    path: 'signin/:sid/:token',
    loadComponent: () => import('src/app/core/components/signin/signin.component').then(c => c.SigninComponent)
  });

  return routes.map(route => {
    if (!hasValue(route.redirectTo)) {
      route.canActivate = Object.assign([], route.canActivate ?? []);
      route.canActivate.push(ContextValidGuard);
    }

    return route;
  });
};
