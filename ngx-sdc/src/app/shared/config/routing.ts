import { RouteConfig } from 'src/app/core/services/context-data/models';
import { ContextDataInfo } from '../constants/context-data';

export enum AppUrls {
  root = '',
  departments = 'departments',
  squads = 'squads',
  applications = 'applications',
  metrics = 'metrics'
}

export const urls: RouteConfig = {
  [AppUrls.root]: { resetContext: true },
  [AppUrls.departments]: { resetContext: true },
  [AppUrls.squads]: { resetContext: true },
  [AppUrls.applications]: { resetContext: false, resetData: [ContextDataInfo.METRICS_DATA] },
  [AppUrls.metrics]: { resetContext: false, requiredData: [ContextDataInfo.METRICS_DATA] }
};
