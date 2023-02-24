import { DataInfo } from '../interfaces/dataInfo';
import { Languages } from './app.constants';

export interface ContextInfo {
  contextData: DataInfo;
  cache: DataInfo;
}

export enum ContextDataNames {
  appConfig = 'app_Config'
}

export class AppConfig {
  public lang = Languages.enGB;
}
