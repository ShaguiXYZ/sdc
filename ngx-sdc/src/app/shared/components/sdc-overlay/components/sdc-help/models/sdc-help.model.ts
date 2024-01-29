import { DataInfo } from 'src/app/core/models';

export interface SdcHelpModel {
  labels: { [key: string]: string };
  entries: DataInfo<SdcHelpEntryModel>;
}

export interface SdcHelpEntryModel {
  indexEntry: string;
  body: string; // path to the body file (SdcHelpBodyModel)
}

export interface SdcHelpBodyModel {
  title?: string;
  media?: string;
  paragraphs: SdcHelpParrafModel[];
}

export interface SdcHelpParrafModel {
  title?: string;
  body: string | string[];
}
