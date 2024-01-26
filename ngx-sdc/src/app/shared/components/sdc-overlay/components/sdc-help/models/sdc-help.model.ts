import { DataInfo } from 'src/app/core/models';

export interface SdcHelpParrafModel {
  title?: string;
  body: string | string[];
}

export interface SdcHelpModel {
  labels: { [key: string]: string };
  entries: DataInfo<SdcHelpEntryModel>;
}

export interface SdcHelpEntryModel {
  indexEntry: string;
  media?: string;
  title: string;
  paragraphs?: SdcHelpParrafModel[];
}
