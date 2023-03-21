import { RouteConfig } from 'src/app/core/services/context-data/models';

// eslint-disable-next-line no-shadow
export enum AppUrls {
  root = '',
  summary = 'summary',
  applications = 'applications'
}

export const urls: RouteConfig = {
  [AppUrls.root]: { resetContext: true },
  [AppUrls.summary]: { resetContext: true },
  [AppUrls.applications]: { resetContext: false, resetData: [] }
};
