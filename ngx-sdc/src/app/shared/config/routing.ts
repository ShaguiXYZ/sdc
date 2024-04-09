import { RouteConfig } from '@shagui/ng-shagui/core';
import { ContextDataInfo } from '../constants';

export enum AppUrls {
  root = '**',
  departments = 'departments',
  squads = 'squads',
  applications = 'applications',
  metrics = 'metrics',
  test = 'test',
  routing = 'routing'
}

export const urls: RouteConfig = {
  [AppUrls.root]: { resetContext: true },
  [AppUrls.departments]: { resetContext: true },
  [AppUrls.squads]: { resetContext: true },
  [AppUrls.applications]: { resetContext: false, resetData: [ContextDataInfo.METRICS_DATA] },
  [AppUrls.metrics]: { resetContext: false, requiredData: [ContextDataInfo.METRICS_DATA] },
  [AppUrls.test]: { resetContext: false }
};
