import { SdcHelpBodyModel } from './sdc-help.model';

export interface SdcHelpConfig {
  body?: SdcHelpBodyModel;
  labels: { [key: string]: string };
  page: string;
  pages: { key: string; indexEntry: string }[];
}
