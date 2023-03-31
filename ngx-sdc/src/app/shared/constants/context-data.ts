import { IComplianceModel } from '../components';

export enum ContextDataInfo {
  SDC_SESSION_DATA = 'SDC_SESSION_DATA',
  APPLICATIONS_DATA = 'APPLICATIONS_DATA',
  METRICS_DATA = 'METRICS_DATA'
}

export interface ApplicationsContextData {
  squad: number;
  page: number;
}

export interface MetricsContextData {
  compliance: IComplianceModel;
}
