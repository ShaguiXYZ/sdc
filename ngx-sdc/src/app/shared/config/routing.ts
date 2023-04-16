import { RouteConfig } from 'src/app/core/services/context-data/models';
import { ContextDataInfo } from '../constants/context-data';

export enum AppUrls {
  root = '',
  summary = 'summary',
  applications = 'applications',
  metrics = 'metrics'
}

export const urls: RouteConfig = {
  [AppUrls.root]: { resetContext: true },
  [AppUrls.summary]: { resetContext: true },
  [AppUrls.applications]: { resetContext: false, resetData: [ContextDataInfo.METRICS_DATA] },
  [AppUrls.metrics]: { resetContext: false, requiredData: [ContextDataInfo.METRICS_DATA] }
};
