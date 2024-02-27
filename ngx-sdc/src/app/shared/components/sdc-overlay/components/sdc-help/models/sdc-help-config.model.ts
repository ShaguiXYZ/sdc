import { DataInfo } from 'src/app/core/models';
import { SdcHelpEntry } from '../constants';
import { SdcHelpBodyModel } from './sdc-help.model';

export interface SdcHelpConfig {
  body?: SdcHelpBodyModel;
  labels: DataInfo;
  page: SdcHelpEntry;
  pages: { key: SdcHelpEntry; indexEntry: string }[];
}

export namespace SdcHelpConfig {
  export const DEFAULT: SdcHelpConfig = {
    labels: {},
    page: SdcHelpEntry.INTRODUCTORY,
    pages: [],
    body: { paragraphs: [] }
  };
}
