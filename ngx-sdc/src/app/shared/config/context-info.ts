import { DataInfo } from '../../core/interfaces/dataInfo';
import { Languages } from './languages';

export interface ContextInfo {
  contextData: DataInfo;
  cache: DataInfo;
}

export enum ContextDataNames {
  appConfig = 'app_Config',
  securityInfo = 'security_info',
  sdcSessionData = 'sdc_session_data'
}

export class AppConfig {
  public lang = Languages.enGB;
}
