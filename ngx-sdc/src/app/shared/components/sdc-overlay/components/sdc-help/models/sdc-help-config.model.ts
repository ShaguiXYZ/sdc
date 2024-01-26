import { SdcHelpEntryModel } from './sdc-help.model';

export interface SdcHelpConfig {
  data?: SdcHelpEntryModel;
  labels: { [key: string]: string };
  page: string;
  pages: { key: string; indexEntry: string }[];
}
