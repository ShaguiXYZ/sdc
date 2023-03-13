import { DataInfo } from '../interfaces/dataInfo';
import { Languages } from './languages';

export interface ContextInfo {
  contextData: DataInfo;
  cache: DataInfo;
}

export enum ContextDataNames {
  appConfig = 'app_Config',
  securityInfo = 'security_info'
}

export class AppConfig {
  public lang = Languages.enGB;
}
