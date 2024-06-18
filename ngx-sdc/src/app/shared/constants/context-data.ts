import { StorageService } from '@shagui/ng-shagui/core';

export const SCHEDULER_PERIOD = 1 * 60 * 1000; // Minutes

export enum ContextDataInfo {
  APP_CONFIG = 'APP_CONFIG',
  APPLICATIONS_DATA = 'APPLICATIONS_DATA',
  BREADCRUMBS_DATA = 'BREADCRUMBS_DATA',
  DEPARTMENTS_DATA = 'DEPARTMENTS_DATA',
  METRICS_DATA = 'METRICS_DATA',
  OVERLAY_DATA = 'OVERLAY_DATA',
  SQUADS_DATA = 'SQUADS_DATA'
}

export const storageAppContextData = (storageService: StorageService) => {
  storageService.create(ContextDataInfo.APPLICATIONS_DATA);
  storageService.create(ContextDataInfo.DEPARTMENTS_DATA);
  storageService.create(ContextDataInfo.SQUADS_DATA);
};

export const retrieveAppContextData = (storageService: StorageService) => {
  storageService.retrieve(ContextDataInfo.APPLICATIONS_DATA);
  storageService.retrieve(ContextDataInfo.DEPARTMENTS_DATA);
  storageService.retrieve(ContextDataInfo.SQUADS_DATA);
};
