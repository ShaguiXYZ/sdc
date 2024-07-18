/* eslint-disable no-redeclare */
/* eslint-disable @typescript-eslint/no-namespace */
import { DataInfo } from '@shagui/ng-shagui/core';
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
